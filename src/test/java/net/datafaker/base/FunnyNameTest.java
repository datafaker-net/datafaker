package net.datafaker.base;

import net.datafaker.AbstractFakerTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FunnyNameTest extends AbstractFakerTest {

    @Test
    void name() {
        assertThat(faker.funnyName().name()).matches("^(\\w+\\.?\\s?'?-?)+$");
    }
}
