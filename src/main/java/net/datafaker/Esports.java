package net.datafaker;

import net.datafaker.core.Faker;

/**
 * @since 0.8.0
 */
public class Esports {
    private final Faker faker;

    protected Esports(final Faker faker) {
        this.faker = faker;
    }

    public String player() {
        return faker.resolve("esport.players");
    }

    public String team() {
        return faker.resolve("esport.teams");
    }

    public String event() {
        return faker.resolve("esport.events");
    }

    public String league() {
        return faker.resolve("esport.leagues");
    }

    public String game() {
        return faker.resolve("esport.games");
    }
}
