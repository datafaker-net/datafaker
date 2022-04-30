package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LeagueOfLegendsTest extends AbstractFakerTest {

    @Test
    void champion() {
        assertThat(faker.leagueOfLegends().champion()).matches("^(\\w+\\.?-?'?\\s?&?\\s?)+$");
    }

    @Test
    void location() {
        assertThat(faker.leagueOfLegends().location()).matches("^(\\w+\\s?)+$");
    }

    @Test
    void quote() {
        assertThat(faker.leagueOfLegends().quote()).isNotEmpty();
    }

    @Test
    void summonerSpell() {
        assertThat(faker.leagueOfLegends().summonerSpell()).matches("^(\\w+\\s?!?)+$");
    }

    @Test
    void masteries() {
        assertThat(faker.leagueOfLegends().masteries()).matches("^(\\w+\\s?'?)+$");
    }

    @Test
    void rank() {
        assertThat(faker.leagueOfLegends().rank()).matches("^\\w+(\\s[IV]+)?$");
    }
}
