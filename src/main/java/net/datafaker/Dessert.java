package net.datafaker;

/**
 * @since 0.9.0
 */
public class Dessert extends AbstractProvider {

    protected Dessert(Faker faker) {
        super(faker);
    }

    /**
     * @return dessert variety e.g. "Cake".
     */
    public String variety() {
        return faker.fakeValuesService().resolve("dessert.variety", this);
    }

    /**
     * @return dessert topping e.g. "Rainbow Sprinkles".
     */
    public String topping() {
        return faker.fakeValuesService().resolve("dessert.topping", this);
    }

    /**
     * @return dessert flavor e.g. "Vanilla".
     */
    public String flavor() {
        return faker.fakeValuesService().resolve("dessert.flavor", this);
    }
}
