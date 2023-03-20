package net.datafaker.annotations;

import java.sql.Timestamp;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FieldFakeProviderTest {

    @Test
    void shouldGenerateEntityWithAnnotationFake() {
        var person = Faker.generate(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.id).isPositive();
        assertThat(person.firstName).isNotNull();
        assertThat(person.lastName).isNotNull();
        assertThat(person.birthDate).isNotNull();
        assertThat(person.birthTimestamp).isNotNull();
    }

    @Test
    void shouldGenerateEntityWithWhenPersonWithDifferentProviderExpressionConfiguration() {
        var person = Faker.generate(PersonWithDifferentProviderExpressionConfiguration.class);

        assertThat(person).isNotNull();
        assertThat(person.defaultName).isEqualTo("Brian Braun");
        assertThat(person.seed).isEqualTo("Phebe Lehner");
        assertThat(person.germanName).isEqualTo("Jonah Hiebl");
        assertThat(person.germanNameAndSeedTwo).isEqualTo("Arwen Byrd");
    }

    @Test
    void shouldGenerateEntityWithExpressionWhenPersonWithDifferentMethodProviderConfiguration() {
        var person = Faker.generate(PersonWithDifferentProviderMethodConfiguration.class);

        assertThat(person).isNotNull();
        assertThat(person.defaultName).isEqualTo("Brian Braun");
        assertThat(person.seed).isEqualTo("Phebe Lehner");
        assertThat(person.germanName).isEqualTo("Jonah Hiebl");
        assertThat(person.germanNameAndSeedTwo).isEqualTo("Arwen Byrd");
    }

    @Test
    void shouldThrowExceptionWhenMethodAndExpressionDefinedForOneField() {
        assertThatThrownBy(() -> Faker.generate(PersonWithWrongDefinition.class))
            .extracting(Throwable::getCause)
            .withFailMessage("Expression and method parameters cannot be defined at the same time.")
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotGenerateEntityWhenPersonWithEmptyProviderDefinition() {
        assertThatThrownBy(() -> Faker.generate(PersonWithEmptyProviderDefinition.class))
            .extracting(Throwable::getCause)
            .withFailMessage("Expression and method parameters were not defined.")
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldGenerateEntityWithoutAnnotatedFields() {
        var person = Faker.generate(PersonWithNotAnnotatedField.class);

        assertThat(person).isNotNull();
        assertThat(person.id).isPositive();
        assertThat(person.name).isNull();
    }

    @Fake(languageTag = "en-de", seed = 1)
    public static class Person {

        @FieldFake(expression = "#{Name.first_name}")
        private String firstName;

        @FieldFake(expression = "#{Name.last_name}")
        private String lastName;

        @FieldFake(expression = "#{date.birthday 'yy DDD hh:mm:ss'}")
        private String birthDate;

        @FieldFake(method = "net.datafaker.providers.base.DateAndTime#birthday")
        private Timestamp birthTimestamp;

        @FieldFake(method = "net.datafaker.providers.base.Number#positive")
        private int id;

        @Override
        public String toString() {
            return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", birthTimestamp=" + birthTimestamp +
                ", id=" + id +
                '}';
        }
    }

    @Fake(languageTag = "en", seed = 10)
    public static class PersonWithDifferentProviderExpressionConfiguration {

        @FieldFake(expression = "#{Name.fullName}", seed = 1)
        private String seed;

        @FieldFake(expression = "#{Name.fullName}", languageTag = "de-de")
        private String germanName;

        @FieldFake(expression = "#{Name.fullName}", languageTag = "de-de", seed = 2)
        private String germanNameAndSeedTwo;

        @FieldFake(expression = "#{Name.fullName}")
        private String defaultName;
    }

    @Fake(languageTag = "en", seed = 10)
    public static class PersonWithDifferentProviderMethodConfiguration {

        @FieldFake(method = "net.datafaker.providers.base.Name#fullName", seed = 1)
        private String seed;

        @FieldFake(method = "net.datafaker.providers.base.Name#fullName", languageTag = "de-de")
        private String germanName;

        @FieldFake(method = "net.datafaker.providers.base.Name#fullName", languageTag = "de-de", seed = 2)
        private String germanNameAndSeedTwo;

        @FieldFake(method = "net.datafaker.providers.base.Name#fullName")
        private String defaultName;
    }

    @Fake(languageTag = "de", seed = 1)
    public static class PersonWithNotAnnotatedField {

        @FieldFake(method = "net.datafaker.providers.base.Number#positive")
        private int id;

        private String name;
    }

    @Fake(languageTag = "de", seed = 1)
    public static class PersonWithWrongDefinition {

        @FieldFake(expression = "#{date.birthday 'yy DDD hh:mm:ss'}", method = "net.datafaker.providers.base.DateAndTime#birthday")
        private String date;
    }

    @Fake(languageTag = "de", seed = 1)
    public static class PersonWithEmptyProviderDefinition {

        @FieldFake
        private String name;
    }

}
