package net.datafaker.base;

/**
 * @since 0.9.0
 */
public class Dessert extends AbstractProvider<IProviders> {

    protected Dessert(BaseFaker faker) {
        super(faker);
    }

    /**
     * @return dessert variety e.g. "Cake".
     */
    public String variety() {
        return resolve("dessert.variety");
    }

    /**
     * @return dessert topping e.g. "Rainbow Sprinkles".
     */
    public String topping() {
        return resolve("dessert.topping");
    }

    /**
     * @return dessert flavor e.g. "Vanilla".
     */
    public String flavor() {
        return resolve("dessert.flavor");
    }
}
