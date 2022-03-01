package net.datafaker;

/**
 * @since 0.8.0
 */
public class RockBand {

    private final Faker faker;

    protected RockBand(Faker faker) {
        this.faker = faker;
    }

    public String name() {
        return faker.resolve("rock_band.name");
    }
}
