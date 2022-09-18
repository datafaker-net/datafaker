package net.datafaker.videogame;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ZeldaTest extends VideoGameFakerTest {

    @Test
    void game() {
        Assertions.assertThat(faker.zelda().game()).matches("[A-Za-z'\\- :]+");
    }

    @Test
    void character() {
        Assertions.assertThat(faker.zelda().character()).matches("(?U)([\\w'\\-.()]+ ?)+");
    }
}
