package net.datafaker.transformations;

import net.datafaker.Faker;
import net.datafaker.sequence.FakeSequence;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JavaObjectTransformerTest {

    public static class Person {
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private Instant registrationDate;
        private int id;
    }

    public static class PersonWithId {
        private final int id;

        public PersonWithId(int id) {
            this.id = id;
        }
    }

    public static class Contact {
        private String firstName;
    }

    public record Client(String firstName, String lastName, String phoneNumber, Instant registrationDate, int id) { }

    public record User(String firstName, String lastName) { }

    private final Faker faker = new Faker();

    @Test
    void javaObjectTest() {
        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(
            field("firstName", () -> faker.name().firstName()),
            field("lastName", () -> faker.name().lastName()),
            field("birthDate", () -> faker.timeAndDate().birthday()),
            field("registrationDate", () -> faker.timeAndDate().past()),
            field("id", () -> faker.number().positive()));

        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Person person = (Person) jTransformer.apply(Person.class, schema);
            assertThat(person.birthDate).isNotNull();
            assertThat(person.lastName).isNotNull();
            assertThat(person.firstName).isNotNull();
            assertThat(person.registrationDate).isNotNull();
            persons.add(person);
        }
        assertThat(persons).hasSize(10);
    }

    @Test
    void javaRecordTest() {
        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(
            field("firstName", () -> faker.name().firstName()),
            field("lastName", () -> faker.name().lastName()),
            field("phoneNumber", () -> faker.phoneNumber().phoneNumberInternational()),
            field("registrationDate", () -> faker.timeAndDate().past()),
            field("id", () -> faker.number().positive()));

        Collection<Client> clients = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Client client = (Client) jTransformer.apply(Client.class, schema);
            assertThat(client.firstName()).isNotNull();
            assertThat(client.lastName()).isNotNull();
            assertThat(client.phoneNumber()).isNotNull();
            assertThat(client.registrationDate()).isNotNull();
            clients.add(client);
        }
        assertThat(clients).hasSize(10);
    }

    @Test
    void javaRecordBatchTransformationKeepsInputOrderAndCountTest() {
        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(
            field("firstName", (Object input) -> ((User) input).firstName() + "-transformed"),
            field("lastName", (Object input) -> ((User) input).lastName() + "-transformed"));

        User first = new User("ALICE", "SMITH");
        User second = new User("BOB", "JONES");
        User third = new User("CAROL", "BROWN");

        Collection<Object> transformed = jTransformer.generate(List.of(first, second, third), schema);

        assertThat(transformed).containsExactly(
            new User("ALICE-transformed", "SMITH-transformed"),
            new User("BOB-transformed", "JONES-transformed"),
            new User("CAROL-transformed", "BROWN-transformed"));
    }

    @Test
    void javaMutableObjectBatchTransformationTest() {
        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(
            field("firstName", () -> "Alice"),
            field("lastName", () -> "Smith"));

        Person original = new Person();
        original.firstName = "OLD_FIRST";
        original.lastName = "OLD_LAST";
        original.id = 42;

        Collection<Object> transformed = jTransformer.generate(List.of(original), schema);

        assertThat(transformed).containsExactly(original);
        assertThat(original.firstName).isEqualTo("Alice");
        assertThat(original.lastName).isEqualTo("Smith");
        assertThat(original.id).isEqualTo(42);
    }

    @Test
    void reusesOneSchemaAcrossDifferentMutableClasses() {
        var transformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(field("firstName", () -> "Alice"));

        assertThat(transformer.generate(List.of(Person.class, Contact.class, Person.class), schema))
            .satisfiesExactly(
                value -> assertThat(((Person) value).firstName).isEqualTo("Alice"),
                value -> assertThat(((Contact) value).firstName).isEqualTo("Alice"),
                value -> assertThat(((Person) value).firstName).isEqualTo("Alice"));
    }

    @Test
    void batchTransformsClassWithoutNoArgConstructor() {
        JavaObjectTransformer transformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(field("id", () -> 42));
        PersonWithId original = new PersonWithId(7);

        assertThat(transformer.generate(List.of(original), schema))
            .singleElement()
            .isInstanceOfSatisfying(PersonWithId.class, transformed -> {
                assertThat(transformed).isNotSameAs(original);
                assertThat(transformed.id).isEqualTo(42);
            });
    }

    @Test
    void javaRecordBatchTransformationFromFakeSequenceTest() {
        JavaObjectTransformer transformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(
            field("firstName", (Object input) -> ((User) input).firstName() + "-new"),
            field("lastName", (Object input) -> ((User) input).lastName()));
        FakeSequence<Object> input = faker.<Object>collection(() -> new User("Alice", "Smith"))
            .len(2)
            .build();

        assertThat(transformer.generate(input, schema))
            .containsExactly(new User("Alice-new", "Smith"), new User("Alice-new", "Smith"));
    }

    @Test
    void batchTransformsRecordsFromFiniteFakeStream() {
        JavaObjectTransformer transformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(
            field("firstName", () -> "Alice"),
            field("lastName", () -> "Smith"));
        FakeSequence<Object> input = faker.<Object>stream(() -> new User("OLD_FIRST", "OLD_LAST"))
            .len(2)
            .build();

        assertThat(transformer.generate(input, schema))
            .containsExactly(new User("Alice", "Smith"), new User("Alice", "Smith"));
    }

    @Test
    void javaInfiniteFakeSequenceBatchTransformationTest() {
        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(
            field("firstName", () -> "Alice"),
            field("lastName", () -> "Smith"));

        FakeSequence<Object> infiniteInput = faker.<Object>stream(() -> new User("OLD_FIRST", "OLD_LAST")).build();

        assertThatThrownBy(() -> jTransformer.generate(infiniteInput, schema))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Should be finite size");
    }

    @Test
    void javaStreamingTest() {
        JavaObjectTransformer jTransformer = (new JavaObjectTransformer()).from(Person.class);
        Schema<Object, ?> schema = Schema.of(
            field("firstName", () -> faker.name().firstName()),
            field("lastName", () -> faker.name().lastName()),
            field("birthDate", () -> faker.timeAndDate().birthday()),
            field("registrationDate", () -> faker.timeAndDate().past())
            );

        Collection<Person> persons = new ArrayList<>();
        jTransformer
            .generateStream(schema, 10)
            .map(object -> (Person) object)
            .forEach(person -> {
                assertThat(person.birthDate).isNotNull();
                assertThat(person.lastName).isNotNull();
                assertThat(person.firstName).isNotNull();
                assertThat(person.registrationDate).isNotNull();
                persons.add(person);
            });

        assertThat(persons).hasSize(10);
    }

    @Test
    void javaEmptyStreamTest() {
        JavaObjectTransformer jTransformer = (new JavaObjectTransformer());
        Schema<Object, ?> schema = Schema.of(
            field("firstName", () -> faker.name().firstName()),
            field("lastName", () -> faker.name().lastName()),
            field("birthDate", () -> faker.timeAndDate().birthday()),
            field("registrationDate", () -> faker.timeAndDate().past())
        );

        assertThat(jTransformer.generateStream(schema, 10).count()).isEqualTo(0);
    }

    @Test
    void javaCollectionTest() {
        JavaObjectTransformer jTransformer = (new JavaObjectTransformer()).from(Person.class);
        Schema<Object, ?> schema = Schema.of(
            field("firstName", () -> faker.name().firstName()),
            field("lastName", () -> faker.name().lastName()),
            field("birthDate", () -> faker.timeAndDate().birthday()),
            field("registrationDate", () -> faker.timeAndDate().past())
        );

        Collection<Person> persons = new ArrayList<>();
        jTransformer
            .generate(schema, 10)
            .stream()
            .map(object -> (Person) object)
            .forEach(person -> {
                assertThat(person.birthDate).isNotNull();
                assertThat(person.lastName).isNotNull();
                assertThat(person.firstName).isNotNull();
                assertThat(person.registrationDate).isNotNull();
                persons.add(person);
            });

        assertThat(persons).hasSize(10);
    }
}
