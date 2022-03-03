package net.datafaker;

/**
 * @since 1.2.0
 */
public class Australia {

    private final Faker faker;

    protected Australia(Faker faker) {
        this.faker = faker;
    }

    public String locations() {
        return faker.fakeValuesService().resolve("australia.locations", this, faker);
    }

    public String animals() {
        return faker.fakeValuesService().resolve("australia.animals", this, faker);
    }

    public String states() {
        return faker.fakeValuesService().resolve("australia.states", this, faker);
    }

}
