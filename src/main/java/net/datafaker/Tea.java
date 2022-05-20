package net.datafaker;

/**
 * @since 1.4.0
 */
public class Tea {

    private final Faker faker;

    protected Tea(Faker faker) {
        this.faker = faker;
    }

    /**
     * This method generates a random tea variety.
     *
     * @return a string of tea variety.
     */
    public String variety() {
        return faker.fakeValuesService().resolve("tea.variety." + type().toLowerCase(), this, faker);
    }

    /**
     * This method generates a random tea type.
     *
     * @return a string of tea type.
     */
    public String type() {
        return faker.fakeValuesService().resolve("tea.type", this, faker);
    }
}
