package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClashOfClansTest extends VideoGameFakerTest {

    @Test
    void troop() {
        assertThat(faker.clashOfClans().troop()).isNotEmpty();
    }

    @Test
    void rank() {
        assertThat(faker.clashOfClans().rank()).isNotEmpty();
    }

    @Test
    void defensiveBuilding() {
        assertThat(faker.clashOfClans().defensiveBuilding()).isNotEmpty();
    }
}
