package net.datafaker;

/**
 * @since 0.8.0
 */
public class Lebowski extends AbstractProvider {

    public Lebowski(final Faker faker) {
        super(faker);
    }

    public String actor() {
        return faker.fakeValuesService().resolve("lebowski.actors", this);
    }

    public String character() {
        return faker.fakeValuesService().resolve("lebowski.characters", this);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("lebowski.quotes", this);
    }
}
