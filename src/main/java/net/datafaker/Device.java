package net.datafaker;

/**
 * @since 1.4.0
 */
public class Device {

    private final Faker faker;

    protected Device(Faker faker) {
        this.faker = faker;
    }

    public String modelName() {
        return faker.fakeValuesService().resolve("device.model_name", this, faker);
    }

    public String platform() {
        return faker.fakeValuesService().resolve("device.platform", this, faker);
    }

    public String manufacturer() {
        return faker.fakeValuesService().resolve("device.manufacturer", this, faker);
    }

    public String serial() {
        return faker.fakeValuesService().resolve("device.serial", this, faker);
    }

}
