package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NatoPhoneticAlphabetTest extends AbstractBaseFakerTest {

    @Test
    void codeWord() {
        assertThat(faker.natoPhoneticAlphabet().codeWord()).isNotEmpty();
    }

}
