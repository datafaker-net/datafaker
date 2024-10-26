package net.datafaker.providers.base;

import java.util.Locale;

/**
 * @since 2.0.3
 */
public class LanguageCode extends AbstractProvider<BaseProviders> {

    private static final String[] languages = Locale.getISOLanguages();

    protected LanguageCode(BaseProviders faker) {
        super(faker);
    }

    /**
     * Returns a random 2-letter language code defined in ISO 639.
     *
     * @return a random 2-letter language code
     */
    public String iso639() {
        int index = faker.random().nextInt(languages.length);
        return languages[index];
    }
}
