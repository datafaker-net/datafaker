package net.datafaker.videogame;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HeroesOfTheStormTest extends VideoGameFakerTest {

    @Test
    void testBattleground() {
        Assertions.assertThat(faker.heroesOfTheStorm().battleground()).isNotEmpty();
    }

    @Test
    void testHeroClass() {
        Assertions.assertThat(faker.heroesOfTheStorm().heroClass()).isNotEmpty();
    }

    @Test
    void testHero() {
        Assertions.assertThat(faker.heroesOfTheStorm().hero()).isNotEmpty();
    }

    @Test
    void testQuote() {
        Assertions.assertThat(faker.heroesOfTheStorm().quote()).isNotEmpty();
    }

}
