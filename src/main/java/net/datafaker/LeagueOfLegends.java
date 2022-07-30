package net.datafaker;

/**
 * @since 0.8.0
 */
public class LeagueOfLegends extends AbstractProvider {

    protected LeagueOfLegends(Faker faker) {
        super(faker);
    }

    public String champion() {
        return faker.fakeValuesService().resolve("games.league_of_legends.champion", this);
    }

    public String location() {
        return faker.fakeValuesService().resolve("games.league_of_legends.location", this);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("games.league_of_legends.quote", this);
    }

    public String summonerSpell() {
        return faker.fakeValuesService().resolve("games.league_of_legends.summoner_spell", this);
    }

    public String masteries() {
        return faker.fakeValuesService().resolve("games.league_of_legends.masteries", this);
    }

    public String rank() {
        return faker.fakeValuesService().resolve("games.league_of_legends.rank", this);
    }
}
