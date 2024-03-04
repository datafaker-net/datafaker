package net.datafaker.providers.base;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * @since 0.8.0
 */
public class Country extends AbstractProvider<BaseProviders> {
    private final String flagUrl;

    private final List<Currency> availableCurrencies;

    protected Country(BaseProviders faker) {
        super(faker);
        this.flagUrl = "https://flags.fmcdn.net/data/flags/w580/";
        this.availableCurrencies = new ArrayList<>(Currency.getAvailableCurrencies());
    }

    public String flag() {
        return flagUrl + resolve("country.code2") + ".png";
    }

    public String countryCode2() {
        return resolve("country.code2");
    }

    public String countryCode3() {
        return resolve("country.code3");
    }

    public String capital() {
        return resolve("country.capital");
    }

    /**
     * @see Currency#getDisplayName()
     * @return a random detailed ISO 4217 currency display name
     */
    public String currency() {
        int randomIndex = faker.random().nextInt(availableCurrencies.size());
        return availableCurrencies.get(randomIndex).getDisplayName();
    }

    /**
     * @see Currency#getCurrencyCode()
     * @return an ISO 4217 currency code
     */
    public String currencyCode() {
        int randomIndex = faker.random().nextInt(availableCurrencies.size());
        return availableCurrencies.get(randomIndex).getCurrencyCode();
    }

    public String name() {
        return resolve("country.name");
    }

}
