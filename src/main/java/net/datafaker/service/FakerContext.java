package net.datafaker.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static net.datafaker.service.FakeValuesService.DEFAULT_LOCALE;

/**
 * Contains the changeable Faker's part which currently contains {@link Locale} and {@link RandomService}.
 */
public class FakerContext {
    private static final Pattern LOCALE = Pattern.compile("[-_]");
    private static final Map<Locale, List<Locale>> LOCALE_2_LOCALES_CHAIN = new HashMap<>();
    private static final Map<String, Locale> STRING_LOCALE_HASH_MAP = new HashMap<>();
    private Locale locale;
    private RandomService randomService;


    /**
     * Resolves YAML file using the most specific path first based on language and country code.
     * 'en_US' would resolve in the following order:
     * <ol>
     * <li>/en-US.yml</li>
     * <li>/en.yml</li>
     * </ol>
     * The search is case-insensitive, so the following will all resolve correctly.  Also, either a hyphen or
     * an underscore can be used when constructing a {@link Locale} instance.  This is legacy behavior and not
     * condoned, but it will work.
     * <ul>
     * <li>EN_US</li>
     * <li>En-Us</li>
     * <li>eN_uS</li>
     * </ul>
     */
    public FakerContext(Locale locale, RandomService randomService) {
        this.locale = locale;
        this.randomService = randomService;
        setCurrentLocale(locale);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setRandomService(RandomService randomService) {
        this.randomService = randomService;
    }

    public Locale getLocale() {
        return locale;
    }

    public RandomService getRandomService() {
        return randomService;
    }

    public List<Locale> getLocaleChain() {
        final List<Locale> res = LOCALE_2_LOCALES_CHAIN.get(locale);
        if (res == null) {
            synchronized (FakerContext.class) {
                return LOCALE_2_LOCALES_CHAIN.get(locale);
            }
        }
        return res;
    }

    /**
     * @return a proper {@link Locale} instance with language and country code set regardless of how
     * it was instantiated.  new Locale("pt-br") will be normalized to a locale constructed
     * with new Locale("pt","BR").
     */
    private Locale normalizeLocale(Locale locale) {
        String localeStr = locale.toString();
        Locale res = STRING_LOCALE_HASH_MAP.get(localeStr);
        if (res != null) {
            return res;
        }
        final String[] parts = LOCALE.split(localeStr);

        if (parts.length == 1) {
            res = new Locale(parts[0]);
        } else {
            res = new Locale(parts[0], parts[1]);
        }
        synchronized (FakerContext.class) {
            STRING_LOCALE_HASH_MAP.put(localeStr, res);
        }
        return res;
    }

    public void setCurrentLocale(Locale locale) {
        Objects.requireNonNull(locale);
        this.locale = normalizeLocale(locale);
        if (LOCALE_2_LOCALES_CHAIN.containsKey(this.locale)) {
            return;
        }
        synchronized (FakerContext.class) {
            LOCALE_2_LOCALES_CHAIN.put(this.locale, localeChain());
        }
    }

    /**
     * Convert the specified locale into a chain of locales used for message resolution. For example:
     * <p>
     * {@link Locale#FRANCE} (fr_FR) to [ fr_FR, anotherTest, en ]
     *
     * @return a list of {@link Locale} instances
     */
    protected List<Locale> localeChain(Locale from) {
        if (DEFAULT_LOCALE.equals(from)) {
            return Collections.singletonList(DEFAULT_LOCALE);
        }

        final Locale normalized = normalizeLocale(from);

        final List<Locale> chain = new ArrayList<>(3);
        chain.add(normalized);
        if (!"".equals(normalized.getCountry()) && !DEFAULT_LOCALE.getLanguage().equals(normalized.getLanguage())) {
            chain.add(new Locale(normalized.getLanguage()));
        }
        chain.add(DEFAULT_LOCALE); // default
        return chain;
    }

    protected List<Locale> localeChain() {
        if (DEFAULT_LOCALE.equals(locale)) {
            return Collections.singletonList(DEFAULT_LOCALE);
        }

        final List<Locale> chain = new ArrayList<>(3);
        chain.add(locale);
        if (!"".equals(locale.getCountry()) && !DEFAULT_LOCALE.getLanguage().equals(locale.getLanguage())) {
            chain.add(new Locale(locale.getLanguage()));
        }
        chain.add(DEFAULT_LOCALE); // default
        return chain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FakerContext that = (FakerContext) o;
        return Objects.equals(locale, that.locale) && Objects.equals(randomService, that.randomService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale, randomService);
    }

    @Override
    public String toString() {
        return "FakerContext{" +
            ", locale=" + locale +
            ", randomService=" + randomService +
            '}';
    }
}
