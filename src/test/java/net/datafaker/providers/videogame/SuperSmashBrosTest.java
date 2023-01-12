package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SuperSmashBrosTest extends VideoGameFakerTest {

    @Test
    void fighter() {
        assertThat(faker.superSmashBros().fighter()).isNotEmpty();
    }

    @Test
    void stage() {
        assertThat(faker.superSmashBros().stage()).isNotEmpty();
    }

}
