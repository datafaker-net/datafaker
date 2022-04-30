package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DragonBallTest extends AbstractFakerTest {

    @Test
    void character() {
        assertThat(faker.dragonBall().character()).matches("^(\\w+\\.?\\s?-?)+$");
    }
}
