package net.datafaker;

/**
 * @since 1.2.0
 */
public class Restaurant {

    private final Faker faker;

    protected Restaurant(Faker faker) {
        this.faker = faker;
    }

    public String namePrefix() {
        return faker.fakeValuesService().resolve("restaurant.name_prefix", this, faker);
    }

    public String nameSuffix() {
        return faker.fakeValuesService().resolve("restaurant.name_suffix", this, faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("restaurant.name", this, faker);
    }

    public String type() {
        return faker.fakeValuesService().resolve("restaurant.type", this, faker);
    }

    public String description() {
        return faker.fakeValuesService().resolve("restaurant.description", this, faker);
    }

    public String review() {
        return faker.fakeValuesService().resolve("restaurant.review", this, faker);
    }

}