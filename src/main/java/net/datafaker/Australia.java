package net.datafaker;

/**
 * @since 1.2.0
 */
public class Australia extends AbstractProvider {

    protected Australia(Faker faker) {
        super(faker);
    }

    public String locations() {
        return faker.fakeValuesService().resolve("australia.locations", this);
    }

    public String animals() {
        return faker.fakeValuesService().resolve("australia.animals", this);
    }

    public String states() {
        return faker.fakeValuesService().resolve("australia.states", this);
    }

}
