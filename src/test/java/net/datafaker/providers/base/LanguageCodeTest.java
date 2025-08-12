package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LanguageCodeTest {

    private final Faker faker = new Faker();

    @Test
    void languageCodeISO639ShouldBe2LettersInLength() {
        final LanguageCode languageCode = faker.languageCode();
        assertThat(languageCode.iso639()).hasSize(2);
    }
}
