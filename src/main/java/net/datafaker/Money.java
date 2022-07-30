package net.datafaker;

/**
 * Support for different kind of money currencies.
 *
 * @since 1.5.0
 */
public class Money extends AbstractProvider {

    public Money(Faker faker) {
        super(faker);
    }

    /**
     * This method returns a currency value in a more descriptive manner like "United States dollar", etc.
     *
     * @return detailed currency value.
     */
    public String currency() {
        return faker.fakeValuesService().resolve("money.currency", this);
    }

    /**
     * Method returns a randomly generated currency value like EUR, USD, etc.
     *
     * @return currency code.
     */
    public String currencyCode() {
        return faker.fakeValuesService().resolve("money.code", this);
    }
}
