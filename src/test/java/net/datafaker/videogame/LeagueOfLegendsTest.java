package net.datafaker.videogame;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class LeagueOfLegendsTest extends VideoGameFakerTest {

    @Test
    void champion() {
        Assertions.assertThat(faker.leagueOfLegends().champion()).matches("^(\\w+\\.?-?'?\\s?&?\\s?)+$");
    }

    @Test
    void location() {
        Assertions.assertThat(faker.leagueOfLegends().location()).matches("^(\\w+\\s?)+$");
    }

    @Test
    void quote() {
        Assertions.assertThat(faker.leagueOfLegends().quote()).isNotEmpty();
    }

    @Test
    void summonerSpell() {
        Assertions.assertThat(faker.leagueOfLegends().summonerSpell()).matches("^(\\w+\\s?!?)+$");
    }

    @Test
    void masteries() {
        Assertions.assertThat(faker.leagueOfLegends().masteries()).matches("^(\\w+\\s?'?)+$");
    }

    @Test
    void rank() {
        Assertions.assertThat(faker.leagueOfLegends().rank()).matches("^\\w+(\\s[IV]+)?$");
    }
}
