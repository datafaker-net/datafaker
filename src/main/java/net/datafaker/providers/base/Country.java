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

    public String currency() {
        return resolve("country.currency");
    }

    public String currencyCode() {
        return resolve("country.currency_code");
    }

    public String name() {
        return resolve("country.name");
    }

}
