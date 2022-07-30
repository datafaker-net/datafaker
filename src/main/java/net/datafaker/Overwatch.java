package net.datafaker;

/**
 * @since 0.8.0
 */
public class Overwatch extends AbstractProvider {

    protected Overwatch(Faker faker) {
        super(faker);
    }

    public String hero() {
        return faker.fakeValuesService().resolve("games.overwatch.heroes", this);
    }

    public String location() {
        return faker.fakeValuesService().resolve("games.overwatch.locations", this);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("games.overwatch.quotes", this);
    }
}
