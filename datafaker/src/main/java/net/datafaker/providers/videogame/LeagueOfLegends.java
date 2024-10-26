package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * League of Legends is a 2009 multiplayer online battle arena video game developed and published by Riot Games.
 *
 * @since 0.8.0
 */
public class LeagueOfLegends extends AbstractProvider<VideoGameProviders> {

    protected LeagueOfLegends(VideoGameProviders faker) {
        super(faker);
    }

    public String champion() {
        return resolve("games.league_of_legends.champion");
    }

    public String location() {
        return resolve("games.league_of_legends.location");
    }

    public String quote() {
        return resolve("games.league_of_legends.quote");
    }

    public String summonerSpell() {
        return resolve("games.league_of_legends.summoner_spell");
    }

    public String masteries() {
        return resolve("games.league_of_legends.masteries");
    }

    public String rank() {
        return resolve("games.league_of_legends.rank");
    }
}
