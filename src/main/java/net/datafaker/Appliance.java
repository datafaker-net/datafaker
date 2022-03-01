package net.datafaker;

/**
 * @since 1.0.0
 */
public class Appliance {
    private final Faker faker;

    protected Appliance(Faker faker) {
        this.faker = faker;
    }

    public String brand() {
        return faker.fakeValuesService().resolve("appliance.brand", this, faker);
    }

    public String equipment() {
        return faker.fakeValuesService().resolve("appliance.equipment", this, faker);
    }
}
