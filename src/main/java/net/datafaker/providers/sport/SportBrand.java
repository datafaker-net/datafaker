package net.datafaker.providers.sport;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Generate random sport wearing brand, e.g. Adidas, Nike, Puma
 */
public class SportBrand extends AbstractProvider<SportProviders> {
    protected SportBrand(SportProviders faker) {
        super(faker);
    }

    public String brand() {
        return resolve("sport_brand.brand");
    }
}
