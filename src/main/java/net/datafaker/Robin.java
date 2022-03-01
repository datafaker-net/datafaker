package net.datafaker;

/**
 * @since 0.8.0
 */
public class Robin {
    private final Faker faker;

    protected Robin(Faker faker) {
        this.faker = faker;
    }

    public String quote() {
        return faker.fakeValuesService().resolve("robin.quotes", this, faker);
    }
}
