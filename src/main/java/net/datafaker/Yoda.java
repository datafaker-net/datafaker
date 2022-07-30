package net.datafaker;

/**
 * @since 0.8.0
 */
public class Yoda extends AbstractProvider {

    protected Yoda(final Faker faker) {
        super(faker);
    }

    public String quote() {
        return faker.resolve("yoda.quotes");
    }
}
