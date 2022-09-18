package net.datafaker.base;

import net.datafaker.AbstractFakerTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NatoPhoneticAlphabetTest extends AbstractFakerTest {

    @Test
    void codeWord() {
        assertThat(faker.natoPhoneticAlphabet().codeWord()).isNotEmpty();
    }

}
