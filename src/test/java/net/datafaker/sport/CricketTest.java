package net.datafaker.sport;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CricketTest extends SportFakerTest {

    @Test
    void teams() {
        assertThat(faker.cricket().teams()).isNotEmpty();
    }

    @Test
    void players() {
        assertThat(faker.cricket().players()).isNotEmpty();
    }

    @Test
    public void formats() {
        assertThat(faker.cricket().formats()).isNotEmpty();
    }

    @Test
    void tournaments() {
        assertThat(faker.cricket().tournaments()).isNotEmpty();
    }

}
