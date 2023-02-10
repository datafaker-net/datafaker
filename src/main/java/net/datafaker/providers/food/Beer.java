package net.datafaker.providers.food;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Beer extends AbstractProvider<FoodProviders> {

    protected Beer(FoodProviders faker) {
        super(faker);
    }

    public String brand() {
        return resolve("beer.brand");
    }

    public String name() {
        return resolve("beer.name");
    }

    public String style() {
        return resolve("beer.style");
    }

    public String hop() {
        return resolve("beer.hop");
    }

    public String yeast() {
        return resolve("beer.yeast");
    }

    public String malt() {
        return resolve("beer.malt");
    }
}
