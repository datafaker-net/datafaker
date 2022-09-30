package net.datafaker.videogame;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


public class SoulKnightTest extends VideoGameFakerTest {

    private static final String SOUL_KNIGHT_VALUE_REGEX = "[a-zA-Z\\d\\-. /():+'’]+";

    @Test
    void charactersTest() {
        Assertions.assertThat(faker.soulKnight().characters()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @Test
    void buffsTest() {
        Assertions.assertThat(faker.soulKnight().buffs()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @Test
    void statuesTest() {
        Assertions.assertThat(faker.soulKnight().statues()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @RepeatedTest(100)
    void weaponsTest() {
        Assertions.assertThat(faker.soulKnight().weapons()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @Test
    void bossesTest() {
        Assertions.assertThat(faker.soulKnight().bosses()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @Test
    void enemiesTest() {
        Assertions.assertThat(faker.soulKnight().enemies()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }
}
