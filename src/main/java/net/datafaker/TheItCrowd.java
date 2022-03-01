package net.datafaker;

/**
 * @since 0.8.0
 */
public class TheItCrowd {

    private final Faker faker;

    protected TheItCrowd(Faker faker) {
        this.faker = faker;
    }

    public String actors() {
        return faker.fakeValuesService().resolve("the_it_crowd.actors", this, faker);
    }

    public String characters() {
        return faker.fakeValuesService().resolve("the_it_crowd.characters", this, faker);
    }

    public String emails() {
        return faker.fakeValuesService().resolve("the_it_crowd.emails", this, faker);
    }

    public String quotes() {
        return faker.fakeValuesService().resolve("the_it_crowd.quotes", this, faker);
    }

}
