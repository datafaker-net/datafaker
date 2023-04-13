package net.datafaker.annotations;

import java.util.Locale;
import java.util.Random;

import net.datafaker.Faker;
import net.datafaker.annotations.dto.Person;
import net.datafaker.service.RandomService;
import net.datafaker.transformations.Schema;
import org.junit.jupiter.api.Test;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;

public class FakeAnnotationTest {

    @Test
    void shouldGenerateEntityWithDefaultSchema() {
        Person person = Faker.populate(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getName()).isEqualTo("Dr Alexis Noël");
    }

    @Test
    void shouldGenerateEntityWithCustomSchema() {
        Person person = Faker.populate(Person.class, customSchema());

        assertThat(person).isNotNull();
        assertThat(person.getName()).isEqualTo("Wildfire Woman");
    }

    @Test
    void shouldGenerateEntityWithDefaultSchemaAndInDefaultSchemaInCurrentClass() {
        DefaultPerson person = Faker.populate(DefaultPerson.class);

        assertThat(person).isNotNull();
        assertThat(person.name).isNotNull();
    }

    public static Schema<Object, ?> defaultSchema() {
        Faker faker = new Faker(Locale.forLanguageTag("fr-en"), new RandomService(new Random(1)));
        return Schema.of(field("name", () -> faker.name().fullName()));
    }

    public static Schema<Object, ?> customSchema() {
        Faker faker = new Faker(Locale.forLanguageTag("de-en"), new RandomService(new Random(1)));
        return Schema.of(field("name", () -> faker.superhero().name()));
    }

    @FakeForSchema("defaultSchema")
    public static class DefaultPerson {

        private String name;
    }
}
