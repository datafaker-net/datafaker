package net.datafaker.transformations;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;

public class JavaObjectTransformerConstructorTest {

    public static class Person {

        private int id;

        public Person() {
        }

        public Person(int id) {
            throw new RuntimeException("This should not be called");
        }
    }

    public static class ProtectedPerson {

        private int id;

        protected ProtectedPerson() {
            throw new RuntimeException("This should not be called");
        }

        public ProtectedPerson(int id) {
            this.id = id;
        }
    }

    public static class PrivatePerson {

        private int id;

        private PrivatePerson() {
            throw new RuntimeException("This should not be called");
        }

        public PrivatePerson(int id) {
            this.id = id;
        }
    }

    private final Faker faker = new Faker();

    @Test
    void javaNoArgConstructorTest() {
        JavaObjectTransformer jTransformer = (new JavaObjectTransformer()).from(Person.class);
        Schema<Object, ?> schema = Schema.of(
            field("id", () -> faker.number().positive())
        );

        jTransformer
            .generate(schema, 10)
            .stream()
            .map(object -> (Person) object)
            .forEach(person -> {
                assertThat(person.id).isNotNull();
            });

    }

    @Test
    void javaNoArgConstructorProtectedTest() {
        JavaObjectTransformer jTransformer = (new JavaObjectTransformer()).from(ProtectedPerson.class);
        Schema<Object, ?> schema = Schema.of(
            field("id", () -> faker.number().positive())
        );

        jTransformer
            .generate(schema, 10)
            .stream()
            .map(object -> (ProtectedPerson) object)
            .forEach(person -> {
                assertThat(person.id).isNotNull();
            });
    }

    @Test
    void javaNoArgConstructorPrivateTest() {
        JavaObjectTransformer jTransformer = (new JavaObjectTransformer()).from(PrivatePerson.class);
        Schema<Object, ?> schema = Schema.of(
            field("id", () -> faker.number().positive())
        );

        jTransformer
            .generate(schema, 10)
            .stream()
            .map(object -> (PrivatePerson) object)
            .forEach(person -> {
                assertThat(person.id).isNotNull();
            });

    }
}
