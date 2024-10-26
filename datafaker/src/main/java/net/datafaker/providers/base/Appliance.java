package net.datafaker.providers.base;

/**
 * @since 1.0.0
 */
public class Appliance extends AbstractProvider<BaseProviders> {

    protected Appliance(BaseProviders faker) {
        super(faker);
    }

    public String brand() {
        return resolve("appliance.brand");
    }

    public String equipment() {
        return resolve("appliance.equipment");
    }
}
