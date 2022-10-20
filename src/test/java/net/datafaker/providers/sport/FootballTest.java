package net.datafaker.providers.sport;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FootballTest extends SportFakerTest {

    @Test
    void teams() {
        assertThat(faker.football().teams()).isNotEmpty();
    }

    @Test
    void players() {
        assertThat(faker.football().players()).isNotEmpty();
    }

    @Test
    void coaches() {
        assertThat(faker.football().coaches()).isNotEmpty();
    }

    @Test
    void competitions() {
        assertThat(faker.football().competitions()).isNotEmpty();
    }

    @Test
    void positions() {
        assertThat(faker.football().positions()).isNotEmpty();
    }

}
