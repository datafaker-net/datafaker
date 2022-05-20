package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SoulKnightTest extends AbstractFakerTest{

    public static final String SOUL_KNIGHT_VALUE_REGEX = "[a-zA-Z\\d\\-. /()']+";

    @Test
    public void charactersTest(){
        assertThat(faker.soulKnight().characters()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @Test
    public void buffsTest(){
        assertThat(faker.soulKnight().buffs()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @Test
    public void statuesTest(){
        assertThat(faker.soulKnight().statues()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @Test
    public void weaponsTest(){
        assertThat(faker.soulKnight().weapons()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @Test
    public void bossesTest(){
        assertThat(faker.soulKnight().bosses()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }

    @Test
    public void enemiesTest(){
        assertThat(faker.soulKnight().enemies()).matches(SOUL_KNIGHT_VALUE_REGEX);
    }
}
