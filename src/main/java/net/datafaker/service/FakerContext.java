package net.datafaker.service;

import net.datafaker.internal.helper.FLocale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
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
    private static final Map<FLocale, List<FLocale>> LOCALE_2_LOCALES_CHAIN = new IdentityHashMap<>();
    private static final Map<FLocale, FLocale> STRING_LOCALE_HASH_MAP = new IdentityHashMap<>();
    public static final List<FLocale> DEFAULT_FLOCALE = Collections.singletonList(DEFAULT_LOCALE);
    private FLocale fLocale;
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
        this.fLocale = FLocale.get(locale);
        this.randomService = randomService;
        setCurrentLocale(locale);
    }

    public void setLocale(Locale locale) {
        this.fLocale = FLocale.get(locale);
    }

    public void setRandomService(RandomService randomService) {
        this.randomService = randomService;
    }

    public Locale getLocale() {
        return fLocale.getLocale();
    }

    public FLocale getFLocale() {
        return fLocale;
    }

    public RandomService getRandomService() {
        return randomService;
    }

    public List<FLocale> getLocaleChain() {
        final List<FLocale> res = LOCALE_2_LOCALES_CHAIN.get(fLocale);
        if (res == null) {
            synchronized (FakerContext.class) {
                return LOCALE_2_LOCALES_CHAIN.get(fLocale);
            }
        }
        return res;
    }

    /**
     * @return a proper {@link Locale} instance with language and country code set regardless of how
     * it was instantiated.  new Locale("pt-br") will be normalized to a locale constructed
     * with new Locale("pt","BR").
     */
    private FLocale normalizeLocale(FLocale flocale) {
        FLocale res = STRING_LOCALE_HASH_MAP.get(flocale);
        if (res != null) {
            return res;
        }
        final String[] parts;
        final Locale locale = flocale.getLocale();
        if (locale.getCountry().isEmpty()) {
            parts = LOCALE.split(locale.getLanguage());
        } else {
            parts = new String[] {locale.getLanguage(), locale.getCountry()};
        }

        if (parts.length == 1) {
            if ((res = FLocale.get(Locale.forLanguageTag(parts[0]))) == null) {
                res = FLocale.get(new Locale(parts[0]));
            }
        } else {
            res = FLocale.get(new Locale(parts[0], parts[1]));
        }
        synchronized (FakerContext.class) {
            STRING_LOCALE_HASH_MAP.put(flocale, res);
        }
        return res;
    }

    public void setCurrentLocale(Locale locale) {
        Objects.requireNonNull(locale);
        this.fLocale = normalizeLocale(FLocale.get(locale));
        if (LOCALE_2_LOCALES_CHAIN.containsKey(this.fLocale)) {
            return;
        }
        synchronized (FakerContext.class) {
            LOCALE_2_LOCALES_CHAIN.put(this.fLocale, localeChain());
        }
    }

    /**
     * Convert the specified locale into a chain of locales used for message resolution. For example:
     * <p>
     * {@link Locale#FRANCE} (fr_FR) to [ fr_FR, anotherTest, en ]
     *
     * @return a list of {@link Locale} instances
     */
    protected List<FLocale> localeChain(Locale from) {
        if (DEFAULT_LOCALE.getLocale().equals(from)) {
            return DEFAULT_FLOCALE;
        }

        final FLocale normalized = normalizeLocale(FLocale.get(from));

        final List<FLocale> chain = new ArrayList<>(3);
        chain.add(normalized);
        if (!"".equals(normalized.getLocale().getCountry()) && !DEFAULT_LOCALE.getLocale().getLanguage().equals(normalized.getLocale().getLanguage())) {
            chain.add(FLocale.get(new Locale(normalized.getLocale().getLanguage())));
        }
        chain.add(DEFAULT_LOCALE); // default
        return chain;
    }

    protected List<FLocale> localeChain() {
        if (DEFAULT_LOCALE == fLocale) {
            return DEFAULT_FLOCALE;
        }

        final List<FLocale> chain = new ArrayList<>(3);
        chain.add(fLocale);
        if (!"".equals(fLocale.getLocale().getCountry()) && !DEFAULT_LOCALE.getLocale().getLanguage().equals(fLocale.getLocale().getLanguage())) {
            chain.add(FLocale.get(new Locale(fLocale.getLocale().getLanguage())));
        }
        chain.add(DEFAULT_LOCALE); // default
        return chain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FakerContext that = (FakerContext) o;
        return Objects.equals(fLocale, that.fLocale) && Objects.equals(randomService, that.randomService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fLocale, randomService);
    }

    @Override
    public String toString() {
        return "FakerContext{" +
            ", locale=" + fLocale +
            ", randomService=" + randomService +
            '}';
    }
}
