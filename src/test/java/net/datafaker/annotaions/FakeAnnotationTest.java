package net.datafaker.annotaions;

import java.sql.Timestamp;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FakeAnnotationTest {

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
    void shouldGenerateEntityWithDefaultAnnotation() {
        var person = Faker.generate(DefaultPerson.class);

        assertThat(person).isNotNull();
        assertThat(person.fullName).isNotNull();
    }

    @Test
    void shouldGenerateEntityWithInheritance() {
        var person = Faker.generate(ParentPerson.class);

        assertThat(person).isNotNull();
        assertThat(person.firstName).isNotNull();
        assertThat(person.childPerson).isNotNull();
        assertThat(person.childPerson.firstName).isNotNull();
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

    @Fake
    public static class DefaultPerson {

        @Provider(expression = "#{Name.full_name}")
        private String fullName;
    }

    @Fake(languageTag = "fr-en", seed = 1)
    public static class ParentPerson {

        @Provider(expression = "#{Name.first_name}")
        private String firstName;

        @EmbeddedFake
        private ChildPerson childPerson;
    }

    @Fake(languageTag = "en-en", seed = 1)
    public static class ChildPerson {

        @Provider(expression = "#{Name.first_name}")
        private String firstName;
    }

    @Fake(languageTag = "en-de", seed = 1)
    public static class Person {

        @Provider(expression = "#{Name.first_name}")
        private String firstName;

        @Provider(expression = "#{Name.last_name}")
        private String lastName;

        @Provider(expression = "#{date.birthday 'yy DDD hh:mm:ss'}")
        private String birthDate;

        @Provider(method = "net.datafaker.providers.base.DateAndTime#birthday")
        private Timestamp birthTimestamp;

        @Provider(expression = "#{numerify '#'}")
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

        @Provider(expression = "#{Name.fullName}", seed = 1)
        private String seed;

        @Provider(expression = "#{Name.fullName}", languageTag = "de-de")
        private String germanName;

        @Provider(expression = "#{Name.fullName}", languageTag = "de-de", seed = 2)
        private String germanNameAndSeedTwo;

        @Provider(expression = "#{Name.fullName}")
        private String defaultName;
    }

    @Fake(languageTag = "en", seed = 10)
    public static class PersonWithDifferentProviderMethodConfiguration {

        @Provider(method = "net.datafaker.providers.base.Name#fullName", seed = 1)
        private String seed;

        @Provider(method = "net.datafaker.providers.base.Name#fullName", languageTag = "de-de")
        private String germanName;

        @Provider(method = "net.datafaker.providers.base.Name#fullName", languageTag = "de-de", seed = 2)
        private String germanNameAndSeedTwo;

        @Provider(method = "net.datafaker.providers.base.Name#fullName")
        private String defaultName;
    }

    @Fake(languageTag = "de", seed = 1)
    public static class PersonWithNotAnnotatedField {

        @Provider(expression = "#{numerify '#'}")
        private int id;

        private String name;
    }

    @Fake(languageTag = "de", seed = 1)
    public static class PersonWithWrongDefinition {

        @Provider(expression = "#{date.birthday 'yy DDD hh:mm:ss'}", method = "net.datafaker.providers.base.DateAndTime#birthday")
        private String date;
    }

    @Fake(languageTag = "de", seed = 1)
    public static class PersonWithEmptyProviderDefinition {

        @Provider
        private String name;
    }
}
