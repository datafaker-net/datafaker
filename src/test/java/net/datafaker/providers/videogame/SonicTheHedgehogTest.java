package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SonicTheHedgehogTest extends VideoGameFakerTest {

    @Test
    void zone() {
        assertThat(faker.sonicTheHedgehog().zone()).isNotEmpty();
    }

    @Test
    void character() {
        assertThat(faker.sonicTheHedgehog().character()).isNotEmpty();
    }

    @Test
    void game() {
        assertThat(faker.sonicTheHedgehog().game()).isNotEmpty();
    }

}
