package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Country extends AbstractProvider<BaseProviders> {
    private final String flagUrl;

    protected Country(BaseProviders faker) {
        super(faker);
        this.flagUrl = "https://flags.fmcdn.net/data/flags/w580/";
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

    public String name() {
        return resolve("country.name");
    }

}
