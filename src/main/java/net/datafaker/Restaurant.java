package net.datafaker;

/**
 * @since 1.2.0
 */
public class Restaurant extends AbstractProvider {

    protected Restaurant(Faker faker) {
        super(faker);
    }

    public String namePrefix() {
        return faker.fakeValuesService().resolve("restaurant.name_prefix", this);
    }

    public String nameSuffix() {
        return faker.fakeValuesService().resolve("restaurant.name_suffix", this);
    }

    public String name() {
        return faker.fakeValuesService().resolve("restaurant.name", this);
    }

    public String type() {
        return faker.fakeValuesService().resolve("restaurant.type", this);
    }

    public String description() {
        return faker.fakeValuesService().resolve("restaurant.description", this);
    }

    public String review() {
        return faker.fakeValuesService().resolve("restaurant.review", this);
    }

}
