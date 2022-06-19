package net.datafaker;

import net.datafaker.core.Faker;

/**
 * @since 0.8.0
 */
public class Zelda {
    private final Faker faker;

    protected Zelda(final Faker faker) {
        this.faker = faker;
    }

    public String game() {
        return faker.resolve("games.zelda.games");
    }

    public String character() {
        return faker.resolve("games.zelda.characters");
    }
}
