package net.datafaker;

/**
 * @since 1.6.0
 */
public class DumbAndDumber extends AbstractProvider {

    protected DumbAndDumber(Faker faker) {
        super(faker);
    }

    public String actor() {
        return faker.resolve("dumb_and_dumber.actors");
    }

    public String character() {
        return faker.resolve("dumb_and_dumber.characters");
    }

    public String quote() {
        return faker.resolve("dumb_and_dumber.quotes");
    }
}
