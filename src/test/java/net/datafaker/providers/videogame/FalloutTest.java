package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class FalloutTest extends VideoGameFakerTest {

    @Test
    void character() {
        assertThat(faker.fallout().character()).isNotEmpty();
    }

    @Test
    void faction() {
        assertThat(faker.fallout().faction()).isNotEmpty();
    }

    @Test
    void location() {
        assertThat(faker.fallout().location()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.fallout().quote()).isNotEmpty();
    }
}
