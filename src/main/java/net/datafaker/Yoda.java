package net.datafaker;

/**
 * @since 0.8.0
 */
public class Yoda {
    private final Faker faker;

    protected Yoda(final Faker faker) {
        this.faker = faker;
    }

    public String quote() {
        return faker.resolve("yoda.quotes");
    }
}
