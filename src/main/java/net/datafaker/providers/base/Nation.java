package net.datafaker.providers.base;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * @since 0.8.0
 */
public class Nation extends AbstractProvider<BaseProviders> {

    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

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
        String[] isoLangs = Locale.getISOLanguages();
        return isoLangs[faker.random().nextInt(isoLangs.length)];
    }

    public String isoCountry() {
        String[] isoCountries = Locale.getISOCountries();
        return isoCountries[faker.random().nextInt(isoCountries.length)];
    }
}
