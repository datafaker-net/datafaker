package net.datafaker.providers.base;


/**
 * Generate random sport wearing brand, e.g. Adidas, Nike, Puma
 */
public class Brand extends AbstractProvider<BaseProviders> {
    protected Brand(BaseProviders faker) {
        super(faker);
    }

    public String sport() {
        return resolve("brand.sport");
    }

    public String car() {
        return faker.vehicle().resolve("vehicle.makes");
    }

    public String watch() {
        return resolve("brand.watch");
    }
}
