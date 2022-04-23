package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NatoPhoneticAlphabetTest extends AbstractFakerTest {

    @Test
    public void codeWord() {
        assertThat(faker.natoPhoneticAlphabet().codeWord()).isNotEmpty();
    }

}
