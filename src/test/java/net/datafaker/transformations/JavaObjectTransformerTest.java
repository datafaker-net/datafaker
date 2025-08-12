package net.datafaker.transformations;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;

public class JavaObjectTransformerTest {

    public static class Person {
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private Instant registrationDate;
        private int id;
    }

    public record Client(String firstName, String lastName, String phoneNumber, Instant registrationDate, int id) { }

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
