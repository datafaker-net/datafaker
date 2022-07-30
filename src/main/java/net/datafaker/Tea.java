package net.datafaker;

/**
 * @since 1.4.0
 */
public class Tea extends AbstractProvider {

    protected Tea(Faker faker) {
        super(faker);
    }

    /**
     * This method generates a random tea variety.
     *
     * @return a string of tea variety.
     */
    public String variety() {
        return faker.fakeValuesService().resolve("tea.variety." + type().toLowerCase(), this);
    }

    /**
     * This method generates a random tea type.
     *
     * @return a string of tea type.
     */
    public String type() {
        return faker.fakeValuesService().resolve("tea.type", this);
    }
}
