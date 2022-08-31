package net.datafaker;

import net.datafaker.service.RandomService;

import java.text.DateFormat;
import java.util.Locale;

public class LocaleFaker extends AbstractProvider {

    private final Locale[] availableLocales = DateFormat.getAvailableLocales();
    private final Locale locale = availableLocales[faker.random().nextInt(availableLocales.length)];

    protected LocaleFaker(Faker faker) {
        super(faker);
    }

    /**
     *
     * @return locale in form: "en-US"
     */
    public String baseLocale() {
        return locale.toString();
    }

    /**
     *
     * @return locale in form: "English (United States)"
     */
    public String displayName() {
        return locale.getDisplayName();
    }
}
