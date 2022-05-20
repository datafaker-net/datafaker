package net.datafaker;

/**
 * @since 1.4.0
 */
public class ElectricalComponents {

    private final Faker faker;

    protected ElectricalComponents(Faker faker) {
        this.faker = faker;
    }

    public String active() {
        return faker.fakeValuesService().resolve("electrical_components.active", this, faker);
    }

    public String passive() {
        return faker.fakeValuesService().resolve("electrical_components.passive", this, faker);
    }

    public String electromechanical() {
        return faker.fakeValuesService().resolve("electrical_components.electromechanical", this, faker);
    }
}
