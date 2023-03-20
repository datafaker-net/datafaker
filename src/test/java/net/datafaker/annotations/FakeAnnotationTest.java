package net.datafaker.annotations;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeAnnotationTest {

    @Test
    void shouldGenerateEntityWithDefaultAnnotation() {
        var person = Faker.generate(DefaultPerson.class);

        assertThat(person).isNotNull();
        assertThat(person.fullName).isNotNull();
    }

    @Fake
    public static class DefaultPerson {

        @FieldFake(expression = "#{Name.full_name}")
        private String fullName;
    }
}
