package net.datafaker.providers.food;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 2.5.2
 */
public class IceCream extends AbstractProvider<FoodProviders> {

    protected IceCream(FoodProviders faker) {
        super(faker);
    }

    public String flavor() {
        return resolve("ice_cream.flavor");
    }

    public String shape() {
        return resolve("ice_cream.shape");
    }

    public String color() {
        return resolve("ice_cream.color");
    }
}
