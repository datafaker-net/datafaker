package net.datafaker.videogame;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ClashOfClansTest extends VideoGameFakerTest {

    @Test
    void troop() {
        Assertions.assertThat(faker.clashOfClans().troop()).isNotEmpty();
    }

    @Test
    void rank() {
        Assertions.assertThat(faker.clashOfClans().rank()).isNotEmpty();
    }

    @Test
    void defensiveBuilding() {
        Assertions.assertThat(faker.clashOfClans().defensiveBuilding()).isNotEmpty();
    }
}
