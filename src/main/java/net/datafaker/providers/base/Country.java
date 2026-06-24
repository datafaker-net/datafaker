package net.datafaker.providers.base;

/**
 * Provides methods for generating country-related data such as names, codes, capitals, and flags.
 * <p>
 * This provider is backed by the data defined in {@code country.yml}.
 *
 * @since 0.8.0
 */
public class Country extends AbstractProvider<BaseProviders> {
    private final String flagUrl;

    protected Country(BaseProviders faker) {
        super(faker);
        this.flagUrl = "https://flags.fmcdn.net/data/flags/w580/";
    }

    /**
     * Generates a URL pointing to the flag image of a random country.
     *
     * @return a flag image URL string
     */
    public String flag() {
        return flagUrl + resolve("country.code2") + ".png";
    }

    /**
     * Returns a random two-letter ISO 3166-1 alpha-2 country code in <strong>lowercase</strong>.
     *
     * @return a 2-letter lowercase country code
     */
    public String countryCode2() {
        return resolve("country.code2");
    }

    /**
     * Returns a random three-letter ISO 3166-1 alpha-3 country code in <strong>lowercase</strong>.
     *
     * @return a 3-letter lowercase country code
     */
    public String countryCode3() {
        return resolve("country.code3");
    }

    /**
     * Returns a random capital city.
     *
     * @return a capital city name
     */
    public String capital() {
        return resolve("country.capital");
    }

    /**
     * @see Money#currency()
     * @return a random detailed ISO 4217 currency display name
     */
    public String currency() {
        return faker.money().currency();
    }

    /**
     * @see Money#currencyCode()
     * @return an ISO 4217 currency code
     */
    public String currencyCode() {
        return faker.money().currencyCode();
    }

    /**
     * Returns a random country name.
     *
     * @return a country name
     */
    public String name() {
        return resolve("country.name");
    }

}
