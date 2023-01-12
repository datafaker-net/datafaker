package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HalfLifeTest extends VideoGameFakerTest {

    @Test
    void character() {
        assertThat(faker.halfLife().character()).isNotEmpty();
    }

    @Test
    void enemy() {
        assertThat(faker.halfLife().enemy()).isNotEmpty();
    }

    @Test
    void location() {
        assertThat(faker.halfLife().location()).isNotEmpty();
    }

}
