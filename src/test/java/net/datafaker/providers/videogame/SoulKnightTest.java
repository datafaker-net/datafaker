package net.datafaker.providers.videogame;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class SoulKnightTest extends VideoGameFakerTest {

    private static final String SOUL_KNIGHT_VALUE_REGEX = "[a-zA-Z\\d\\-. /():+'’]+";

    @Test
    void charactersTest() {
        assertThat(faker.soulKnight().characters()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @Test
    void buffsTest() {
        assertThat(faker.soulKnight().buffs()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @Test
    void statuesTest() {
        assertThat(faker.soulKnight().statues()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @RepeatedTest(100)
    void weaponsTest() {
        assertThat(faker.soulKnight().weapons()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @Test
    void bossesTest() {
        assertThat(faker.soulKnight().bosses()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @Test
    void enemiesTest() {
        assertThat(faker.soulKnight().enemies()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }
}
