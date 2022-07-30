package net.datafaker;

/**
 * @since 0.8.0
 */
public class Matz extends AbstractProvider {

    protected Matz(final Faker faker) {
        super(faker);
    }

    public String quote() {
        return faker.resolve("matz.quotes");
    }
}
