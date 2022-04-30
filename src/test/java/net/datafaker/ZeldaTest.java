package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ZeldaTest extends AbstractFakerTest {

    @Test
    void game() {
        assertThat(faker.zelda().game()).matches("[A-Za-z'\\- :]+");
    }

    @Test
    void character() {
        assertThat(faker.zelda().character()).matches("(?U)([\\w'\\-.()]+ ?)+");
    }
}
