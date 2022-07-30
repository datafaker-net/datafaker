package net.datafaker;

/**
 * @since 0.8.0
 */
public class TheItCrowd extends AbstractProvider {

    protected TheItCrowd(Faker faker) {
        super(faker);
    }

    public String actors() {
        return faker.fakeValuesService().resolve("the_it_crowd.actors", this);
    }

    public String characters() {
        return faker.fakeValuesService().resolve("the_it_crowd.characters", this);
    }

    public String emails() {
        return faker.fakeValuesService().resolve("the_it_crowd.emails", this);
    }

    public String quotes() {
        return faker.fakeValuesService().resolve("the_it_crowd.quotes", this);
    }

}
