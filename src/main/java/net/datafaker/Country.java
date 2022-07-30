package net.datafaker;

/**
 * @since 0.8.0
 */
public class Country extends AbstractProvider {
    private final String flagUrl;

    protected Country(Faker faker) {
        super(faker);
        this.flagUrl = "https://flags.fmcdn.net/data/flags/w580/";
    }

    public String flag() {
        return flagUrl + faker.fakeValuesService().resolve("country.code2", this) + ".png";
    }

    public String countryCode2() {
        return faker.fakeValuesService().resolve("country.code2", this);
    }

    public String countryCode3() {
        return faker.fakeValuesService().resolve("country.code3", this);
    }

    public String capital() {
        return faker.fakeValuesService().resolve("country.capital", this);
    }

    public String currency() {
        return faker.fakeValuesService().resolve("country.currency", this);
    }

    public String currencyCode() {
        return faker.fakeValuesService().resolve("country.currency_code", this);
    }

    public String name() {
        return faker.fakeValuesService().resolve("country.name", this);
    }

}
