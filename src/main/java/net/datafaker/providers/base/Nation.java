package net.datafaker.providers.base;

import java.util.Locale;

/**
 * @since 0.8.0
 */
public class Nation extends AbstractProvider<BaseProviders> {
    private static final String[] isoLanguages = Locale.getISOLanguages();
    private static final String[] isoCountries = Locale.getISOCountries();

    protected Nation(BaseProviders faker) {
        super(faker);
    }

    public String nationality() {
        return resolve("nation.nationality");
    }

    public String language() {
        return resolve("nation.language");
    }

    public String capitalCity() {
        return resolve("nation.capital_city");
    }

    public String flag() {
        return resolve("nation.flag");
    }

    public String isoLanguage() {
        return faker.options().option(isoLanguages);
    }

    public String isoCountry() {
        return faker.options().option(isoCountries);
    }
}
