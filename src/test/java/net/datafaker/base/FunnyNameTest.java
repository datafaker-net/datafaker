package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FunnyNameTest extends AbstractBaseFakerTest {

    @Test
    void name() {
        assertThat(faker.funnyName().name()).matches("^(\\w+\\.?\\s?'?-?)+$");
    }
}
