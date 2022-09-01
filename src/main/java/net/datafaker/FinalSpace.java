package net.datafaker;

/**
 * Final Space is an adult animated space opera comedy drama television series.
 *
 * @since 1.6.0
 */
public class FinalSpace extends AbstractProvider {

    protected FinalSpace(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.resolve("final_space.characters");
    }

    public String vehicle() {
        return faker.resolve("final_space.vehicles");
    }

    public String quote() {
        return faker.resolve("final_space.quotes");
    }

}
