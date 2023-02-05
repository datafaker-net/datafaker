package net.datafaker.providers.base;


/**
 * Generate random sport wearing brand, car brand or watch brand. Only generate brand by types of products.
 * If some brands already exists in another faker, you can integrate this faker in the Brand faker
 *
 * @since 1.8.0
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
