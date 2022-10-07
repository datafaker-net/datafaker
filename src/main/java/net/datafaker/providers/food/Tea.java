package net.datafaker.providers.food;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.4.0
 */
public class Tea extends AbstractProvider<FoodProviders> {

    protected Tea(FoodProviders faker) {
        super(faker);
    }

    /**
     * This method generates a random tea variety.
     *
     * @return a string of tea variety.
     */
    public String variety() {
        return resolve("tea.variety." + type().toLowerCase());
    }

    /**
     * This method generates a random tea type.
     *
     * @return a string of tea type.
     */
    public String type() {
        return resolve("tea.type");
    }
}
