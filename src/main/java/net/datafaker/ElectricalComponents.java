package net.datafaker;

/**
 * @since 1.4.0
 */
public class ElectricalComponents extends AbstractProvider {

    protected ElectricalComponents(Faker faker) {
        super(faker);
    }

    public String active() {
        return faker.fakeValuesService().resolve("electrical_components.active", this);
    }

    public String passive() {
        return faker.fakeValuesService().resolve("electrical_components.passive", this);
    }

    public String electromechanical() {
        return faker.fakeValuesService().resolve("electrical_components.electromechanical", this);
    }
}
