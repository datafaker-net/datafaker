package net.datafaker.transformations;

import net.datafaker.AbstractFakerTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;

public class JavaObjectTransformerTest extends AbstractFakerTest {

    public static class Person {
        private String firstName;
        private String lastName;
        private Date birthDate;
        private int id;

    }

    @Test
    void javaObjectTest() {
        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        Schema<Object, ?> schema = Schema.of(
            field("firstName", () -> faker.name().firstName()),
            field("lastName", () -> faker.name().lastName()),
            field("birthDate", () -> faker.date().birthday()),
            field("id", () -> faker.number().positive()));

        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Person person = (Person) jTransformer.apply(Person.class, schema);
            assertThat(person.birthDate).isNotNull();
            assertThat(person.lastName).isNotNull();
            assertThat(person.firstName).isNotNull();
            persons.add(person);
        }
        assertThat(persons).hasSize(10);
    }
}
