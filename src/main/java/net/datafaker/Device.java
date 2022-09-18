package net.datafaker;

/**
 * @since 1.4.0
 */
public class Device extends AbstractProvider<IProviders> {

    protected Device(BaseFaker faker) {
        super(faker);
    }

    public String modelName() {
        return resolve("device.model_name");
    }

    public String platform() {
        return resolve("device.platform");
    }

    public String manufacturer() {
        return resolve("device.manufacturer");
    }

    public String serial() {
        return resolve("device.serial");
    }

}
