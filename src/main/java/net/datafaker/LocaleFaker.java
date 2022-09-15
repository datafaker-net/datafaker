package net.datafaker;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class LocaleFaker extends AbstractProvider {

    private final List<Locale> availableLocales = Arrays.stream(DateFormat.getAvailableLocales())
        .filter(locale -> !locale.getDisplayName().isEmpty())
        .collect(Collectors.toList());

    protected LocaleFaker(Faker faker) {
        super(faker);
    }

    /**
     *
     * @return locale in form: "en_US or en"
     */
    public String baseLocale() {
        final Locale locale = availableLocales.get(faker.random().nextInt(availableLocales.size()));
        String language = locale.getLanguage();

        String country = locale.getCountry();
        if (!country.isEmpty()) {
            language += "_" + country;
        }
        return language;
    }

    /**
     *
     * @return locale in form: "English (United States) or English"
     */
    public String displayName() {
        int randomIndex = faker.random().nextInt(availableLocales.size());
        Locale locale = availableLocales.get(randomIndex);

        String displayLanguage = locale.getDisplayLanguage(Locale.ENGLISH);
        String displayCountry = locale.getDisplayCountry(Locale.ENGLISH);
        if (!displayCountry.isEmpty()) {
            displayLanguage += " (" + displayCountry + ")";
        }
        return displayLanguage;
    }
}
