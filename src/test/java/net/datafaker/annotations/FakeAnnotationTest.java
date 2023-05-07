package net.datafaker.annotations;

import java.util.Locale;
import java.util.Random;

import net.datafaker.Faker;
import net.datafaker.annotations.dto.Person;
import net.datafaker.annotations.dto.PersonJavaRecord;
import net.datafaker.service.RandomService;
import net.datafaker.transformations.Schema;
import org.junit.jupiter.api.Test;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;

public class FakeAnnotationTest {

    @Test
    void shouldGenerateEntityWithDefaultSchema() {
        var person = Faker.populate(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getName()).isEqualTo("Dr Alexis Noël");
    }

    @Test
    void shouldGenerateEntityFromJavaRecordWithDefaultSchema() {
        var person = Faker.populate(PersonJavaRecord.class);

        assertThat(person).isNotNull();
        assertThat(person.name()).isEqualTo("Dr Alexis Noël");
    }

    @Test
    void shouldGenerateEntityWithCustomSchema() {
        var person = Faker.populate(Person.class, customSchema());

        assertThat(person).isNotNull();
        assertThat(person.getName()).isEqualTo("Wildfire Woman");
    }

    @Test
    void shouldGenerateEntityFromJavaRecordWithCustomSchema() {
        var person = Faker.populate(PersonJavaRecord.class, customSchema());

        assertThat(person).isNotNull();
        assertThat(person.name()).isEqualTo("Wildfire Woman");
    }

    @Test
    void shouldGenerateEntityWithCustomSchemaWhenClassTemplateWithoutAnnotation() {
        var person = Faker.populate(SimplePerson.class, customSchema());

        assertThat(person).isNotNull();
        assertThat(person.name).isEqualTo("Wildfire Woman");
    }

    @Test
    void shouldGenerateEntityFromJavaRecordWithCustomSchemaWhenClassTemplateWithoutAnnotation() {
        var person = Faker.populate(SimplePersonJavaRecord.class, customSchema());

        assertThat(person).isNotNull();
        assertThat(person.name).isEqualTo("Wildfire Woman");
    }

    @Test
    void shouldGenerateEntityWithDefaultSchemaAndInDefaultSchemaInCurrentClass() {
        var person = Faker.populate(DefaultPerson.class);

        assertThat(person).isNotNull();
        assertThat(person.name).isNotNull();
    }

    @Test
    void shouldGenerateEntityFromJavaRecordWithDefaultSchemaAndInDefaultSchemaInCurrentClass() {
        var person = Faker.populate(DefaultPersonJavaRecord.class);

        assertThat(person).isNotNull();
        assertThat(person.name).isNotNull();
    }

    public static Schema<Object, ?> defaultSchema() {
        var faker = new Faker(Locale.forLanguageTag("fr-en"), new RandomService(new Random(1)));
        return Schema.of(field("name", () -> faker.name().fullName()));
    }

    public static Schema<Object, ?> customSchema() {
        var faker = new Faker(Locale.forLanguageTag("de-en"), new RandomService(new Random(1)));
        return Schema.of(field("name", () -> faker.superhero().name()));
    }

    @FakeForSchema("defaultSchema")
    public static class DefaultPerson {

        private String name;
    }

    public static class SimplePerson {

        private String name;
    }

    @FakeForSchema("defaultSchema")
    public record DefaultPersonJavaRecord(String name) { }

    public record SimplePersonJavaRecord(String name) { }
}
