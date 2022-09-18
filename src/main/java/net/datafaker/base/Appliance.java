package net.datafaker.base;

/**
 * @since 1.0.0
 */
public class Appliance extends AbstractProvider<IProviders> {

    protected Appliance(BaseFaker faker) {
        super(faker);
    }

    public String brand() {
        return resolve("appliance.brand");
    }

    public String equipment() {
        return resolve("appliance.equipment");
    }
}
