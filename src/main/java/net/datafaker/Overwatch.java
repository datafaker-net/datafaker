package net.datafaker;

import net.datafaker.core.Faker;

/**
 * @since 0.8.0
 */
public class Overwatch {
    private final Faker faker;

    protected Overwatch(Faker faker) {
        this.faker = faker;
    }

    public String hero() {
        return faker.fakeValuesService().resolve("games.overwatch.heroes", this, faker);
    }

    public String location() {
        return faker.fakeValuesService().resolve("games.overwatch.locations", this, faker);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("games.overwatch.quotes", this, faker);
    }
}
