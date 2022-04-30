package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HorseTest extends AbstractFakerTest {

    @Test
    void name() {
        assertThat(faker.horse().name()).isNotEmpty();
    }

    @Test
    void breed() {
        assertThat(faker.horse().breed()).isNotEmpty();
    }

}
