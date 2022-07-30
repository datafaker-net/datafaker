package net.datafaker;

/**
 * @since 0.8.0
 */
public class RockBand extends AbstractProvider {

    protected RockBand(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.resolve("rock_band.name");
    }
}
