package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HeroesOfTheStormTest extends VideoGameFakerTest {

    @Test
    void testBattleground() {
        assertThat(faker.heroesOfTheStorm().battleground()).isNotEmpty();
    }

    @Test
    void testHeroClass() {
        assertThat(faker.heroesOfTheStorm().heroClass()).isNotEmpty();
    }

    @Test
    void testHero() {
        assertThat(faker.heroesOfTheStorm().hero()).isNotEmpty();
    }

    @Test
    void testQuote() {
        assertThat(faker.heroesOfTheStorm().quote()).isNotEmpty();
    }

}
