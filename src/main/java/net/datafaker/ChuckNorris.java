package net.datafaker;

/**
 * @since 0.8.0
 */
public class ChuckNorris {
    private final Faker faker;

    protected ChuckNorris(Faker faker) {
        this.faker = faker;
    }

    public String fact() {
        return faker.fakeValuesService().resolve("chuck_norris.fact", this, faker);
    }
}
