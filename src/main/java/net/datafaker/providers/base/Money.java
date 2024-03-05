package net.datafaker.providers.base;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Support for different kind of money currencies.
 *
 * @since 1.5.0
 */
public class Money extends AbstractProvider<BaseProviders> {

    private final List<Currency> availableCurrencies;

    public Money(BaseProviders faker) {
        super(faker);
        this.availableCurrencies = new ArrayList<>(Currency.getAvailableCurrencies());
    }

    /**
     * This method returns a currency value in a more descriptive manner like "United States dollar", etc.
     *
     * @see Currency#getDisplayName()
     * @return detailed currency value.
     */
    public String currency() {
        int randomIndex = faker.random().nextInt(availableCurrencies.size());
        return availableCurrencies.get(randomIndex).getDisplayName();
    }

    /**
     * <p>Returns a random ISO 4217 currency code (ex. EUR, USD)</p>
     *
     * @see Currency#getCurrencyCode()
     * @return an ISO 4217 currency code
     */
    public String currencyCode() {
        int randomIndex = faker.random().nextInt(availableCurrencies.size());
        return availableCurrencies.get(randomIndex).getCurrencyCode();
    }

    /**
     * <p>Returns the 3 digit ISO 4217 numeric code of a random currency. Read more: </p>
     *
     * @see Currency#getNumericCodeAsString()
     * @return an ISO 4217 currency numeric code
     * @since 2.2.0
     */
    public String currencyNumericCode() {
        int randomIndex = faker.random().nextInt(availableCurrencies.size());
        return availableCurrencies.get(randomIndex).getNumericCodeAsString();
    }

    /**
     * @see Currency#getSymbol()
     * @return an ISO 4217 currency symbol
     * @since 2.2.0
     */
    public String currencySymbol() {
        int randomIndex = faker.random().nextInt(availableCurrencies.size());
        return availableCurrencies.get(randomIndex).getSymbol();
    }

}
