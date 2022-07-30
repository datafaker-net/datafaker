package net.datafaker;

/**
 * @since 1.0.0
 */
public class Appliance extends AbstractProvider {

    protected Appliance(Faker faker) {
        super(faker);
    }

    public String brand() {
        return faker.fakeValuesService().resolve("appliance.brand", this);
    }

    public String equipment() {
        return faker.fakeValuesService().resolve("appliance.equipment", this);
    }
}
