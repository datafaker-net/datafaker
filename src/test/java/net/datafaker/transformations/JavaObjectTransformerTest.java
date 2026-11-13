package net.datafaker.transformations;

import net.datafaker.Faker;
import net.datafaker.sequence.FakeSequence;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
    void javaRecordBatchTransformationTest() {
        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(
            field("firstName", () -> "Alice"),
            field("lastName", () -> "Smith"));

        User original = new User("OLD_FIRST", "OLD_LAST");

        Collection<Object> transformed = jTransformer.generate(List.of(original), schema);

        assertThat(transformed)
            .hasSize(1)
            .containsExactly(new User("Alice", "Smith"));
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
    void javaEmptyBatchTransformationTest() {
        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(
            field("firstName", () -> "Alice"),
            field("lastName", () -> "Smith"));

        assertThat(jTransformer.generate(List.of(), schema)).isEmpty();
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
    void javaRecordBatchTransformationFromFakeSequenceTest() {
        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(
            field("firstName", (Object input) -> ((User) input).firstName() + "-new"),
            field("lastName", (Object input) -> ((User) input).lastName()));

        List<User> produced = new ArrayList<>();
        Iterable<Object> input = faker.<Object>collection(
                () -> trackedUser("0", produced),
                () -> trackedUser("1", produced),
                () -> trackedUser("2", produced))
            .len(3)
            .build();

        Collection<Object> transformed = jTransformer.generate(input, schema);

        assertThat(produced).hasSize(3);
        assertThat(transformed)
            .hasSize(3)
            .containsExactlyElementsOf(produced.stream()
                .map(user -> new User(user.firstName() + "-new", user.lastName()))
                .toList());
    }

    private static User trackedUser(String suffix, List<User> produced) {
        User user = new User("FIRST_" + suffix, "LAST_" + suffix);
        produced.add(user);
        return user;
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
    void javaBatchTransformationConsumesLazyInputBeforeTransformingTest() {
        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        List<String> events = new ArrayList<>();
        Iterable<Object> lazyInput = () -> new Iterator<Object>() {
            private int index;

            @Override
            public boolean hasNext() {
                return index < 2;
            }

            @Override
            public Object next() {
                User user = new User("FIRST_" + index, "LAST_" + index);
                events.add("read-" + index);
                index++;
                return user;
            }
        };
        Schema<Object, ?> schema = Schema.of(
            field("firstName", (Object input) -> {
                events.add("transform");
                return ((User) input).firstName() + "-new";
            }),
            field("lastName", (Object input) -> {
                events.add("transform");
                return ((User) input).lastName() + "-new";
            }));

        Collection<Object> transformed = jTransformer.generate(lazyInput, schema);

        assertThat(transformed).containsExactly(
            new User("FIRST_0-new", "LAST_0-new"),
            new User("FIRST_1-new", "LAST_1-new"));
        assertThat(events).containsExactly(
            "read-0", "read-1",
            "transform", "transform", "transform", "transform");
    }

    @Test
    void javaBatchTransformationWhenSchemaMutatesInputListTest() {
        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        List<Object> source = new ArrayList<>(List.of(
            new User("FIRST_0", "LAST_0"),
            new User("FIRST_1", "LAST_1")));
        Schema<Object, ?> schema = Schema.of(
            field("firstName", (Object input) -> {
                source.remove(0);
                return ((User) input).firstName() + "-new";
            }),
            field("lastName", (Object input) -> ((User) input).lastName()));

        Collection<Object> transformed = jTransformer.generate(source, schema);

        assertThat(source).isEmpty();
        assertThat(transformed).containsExactly(
            new User("FIRST_0-new", "LAST_0"),
            new User("FIRST_1-new", "LAST_1"));
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
