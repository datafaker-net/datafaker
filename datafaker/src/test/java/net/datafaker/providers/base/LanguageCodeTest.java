package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LanguageCodeTest extends BaseFakerTest<BaseFaker> {

    @Test
    void languageCodeISO639ShouldBe2LettersInLength() {
        final LanguageCode languageCode = faker.languageCode();
        assertThat(languageCode.iso639()).hasSize(2);
    }
}
