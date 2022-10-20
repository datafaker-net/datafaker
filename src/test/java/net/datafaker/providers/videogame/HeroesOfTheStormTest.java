package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HeroesOfTheStormTest extends VideoGameFakerTest {

    @Test
    void battleground() {
        assertThat(faker.heroesOfTheStorm().battleground()).isNotEmpty();
    }

    @Test
    void heroClass() {
        assertThat(faker.heroesOfTheStorm().heroClass()).isNotEmpty();
    }

    @Test
    void hero() {
        assertThat(faker.heroesOfTheStorm().hero()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.heroesOfTheStorm().quote()).isNotEmpty();
    }

}
