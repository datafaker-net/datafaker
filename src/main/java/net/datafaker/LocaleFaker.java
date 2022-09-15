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
     * @return locale in form: "en_US"
     */
    public String baseLocale() {
        final Locale locale = availableLocales.get(faker.random().nextInt(availableLocales.size()));
        return locale.toString();
    }

    /**
     *
     * @return locale in form: "English (United States) or en_IN"
     */
    public String displayName() {
        int randomIndex = faker.random().nextInt(availableLocales.size());
        Locale locale = availableLocales.get(randomIndex);
        return locale.getDisplayName();
    }
}
