package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LeagueOfLegendsTest extends AbstractFakerTest {

    @Test
    public void champion() {
        assertThat(faker.leagueOfLegends().champion()).matches("^(\\w+\\.?-?'?\\s?&?\\s?)+$");
    }

    @Test
    public void location() {
        assertThat(faker.leagueOfLegends().location()).matches("^(\\w+\\s?)+$");
    }

    @Test
    public void quote() {
        assertFalse(faker.leagueOfLegends().quote().isEmpty());
    }

    @Test
    public void summonerSpell() {
        assertThat(faker.leagueOfLegends().summonerSpell()).matches("^(\\w+\\s?!?)+$");
    }

    @Test
    public void masteries() {
        assertThat(faker.leagueOfLegends().masteries()).matches("^(\\w+\\s?'?)+$");
    }

    @Test
    public void rank() {
        assertThat(faker.leagueOfLegends().rank()).matches("^\\w+(\\s[IV]+)?$");
    }
}
