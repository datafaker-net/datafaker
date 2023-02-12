package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;


class LeagueOfLegendsTest extends VideoGameFakerTest {

    private final LeagueOfLegends leagueOfLegends = getFaker().leagueOfLegends();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(leagueOfLegends::champion, "games.league_of_legends.champion"),
            TestSpec.of(leagueOfLegends::location, "games.league_of_legends.location"),
            TestSpec.of(leagueOfLegends::masteries, "games.league_of_legends.masteries"),
            TestSpec.of(leagueOfLegends::quote, "games.league_of_legends.quote"),
            TestSpec.of(leagueOfLegends::rank, "games.league_of_legends.rank"),
            TestSpec.of(leagueOfLegends::summonerSpell, "games.league_of_legends.summoner_spell")
        );
    }
}
