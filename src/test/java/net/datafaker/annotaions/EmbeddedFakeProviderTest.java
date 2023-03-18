package net.datafaker.annotaions;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmbeddedFakeProviderTest {

    @Test
    void shouldGenerateEntityWithInheritance() {
        var person = Faker.generate(ParentPerson.class);

        assertThat(person).isNotNull();
        assertThat(person.firstName).isNotNull();
        assertThat(person.childPerson).isNotNull();
        assertThat(person.childPerson.firstName).isNotNull();
    }

    @Fake(languageTag = "fr-en", seed = 1)
    public static class ParentPerson {

        @FieldFake(expression = "#{Name.first_name}")
        private String firstName;

        @EmbeddedFake
        private ChildPerson childPerson;
    }

    @Fake(languageTag = "en-en", seed = 1)
    public static class ChildPerson {

        @FieldFake(expression = "#{Name.first_name}")
        private String firstName;
    }
}
