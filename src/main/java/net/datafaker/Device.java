package net.datafaker;

/**
 * @since 1.4.0
 */
public class Device extends AbstractProvider {

    protected Device(Faker faker) {
        super(faker);
    }

    public String modelName() {
        return faker.fakeValuesService().resolve("device.model_name", this);
    }

    public String platform() {
        return faker.fakeValuesService().resolve("device.platform", this);
    }

    public String manufacturer() {
        return faker.fakeValuesService().resolve("device.manufacturer", this);
    }

    public String serial() {
        return faker.fakeValuesService().resolve("device.serial", this);
    }

}
