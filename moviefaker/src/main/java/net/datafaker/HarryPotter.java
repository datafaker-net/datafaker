package net.datafaker;

/**
 * @since 0.8.0
 */
public class HarryPotter extends MovieProvider {

    protected HarryPotter(final MovieFaker faker) {
        super(faker);
    }

    public String character() {
        return faker.resolve("harry_potter.characters");
    }

    public String location() {
        return faker.resolve("harry_potter.locations");
    }

    public String quote() {
        return faker.resolve("harry_potter.quotes");
    }

    public String book() {
        return faker.resolve("harry_potter.books");
    }

    public String house() {
        return faker.resolve("harry_potter.houses");
    }

    public String spell() {
        return faker.resolve("harry_potter.spells");
    }
}
