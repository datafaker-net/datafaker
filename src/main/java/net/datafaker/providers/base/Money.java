package net.datafaker.providers.base;

/**
 * Support for different kind of money currencies.
 *
 * @since 1.5.0
 */
public class Money extends AbstractProvider<BaseProviders> {

    public Money(BaseProviders faker) {
        super(faker);
    }

    /**
     * This method returns a currency value in a more descriptive manner like "United States dollar", etc.
     *
     * @return detailed currency value.
     */
    public String currency() {
        return resolve("money.currency");
    }

    /**
     * Method returns a randomly generated currency value like EUR, USD, etc.
     *
     * @return currency code.
     */
    public String currencyCode() {
        return resolve("money.code");
    }
}
