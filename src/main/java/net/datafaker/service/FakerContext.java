package net.datafaker.service;

import net.datafaker.internal.helper.SingletonLocale;

import java.util.ArrayList;
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
    private static final Map<SingletonLocale, List<SingletonLocale>> LOCALE_2_LOCALES_CHAIN = new IdentityHashMap<>();
    private static final Map<SingletonLocale, SingletonLocale> STRING_LOCALE_HASH_MAP = new IdentityHashMap<>();
    public static final List<SingletonLocale> DEFAULT_SINGLETON_LOCALE_LIST = List.of(DEFAULT_LOCALE);
    private SingletonLocale sLocale;
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
        this.sLocale = SingletonLocale.get(locale);
        this.randomService = randomService;
        setCurrentLocale(locale);
    }

    public void setLocale(Locale locale) {
        this.sLocale = SingletonLocale.get(locale);
    }

    public void setRandomService(RandomService randomService) {
        this.randomService = randomService;
    }

    public Locale getLocale() {
        return sLocale.getLocale();
    }

    public SingletonLocale getSingletonLocale() {
        return sLocale;
    }

    public RandomService getRandomService() {
        return randomService;
    }

    public List<SingletonLocale> getLocaleChain() {
        final List<SingletonLocale> res = LOCALE_2_LOCALES_CHAIN.get(sLocale);
        if (res == null) {
            synchronized (FakerContext.class) {
                return LOCALE_2_LOCALES_CHAIN.get(sLocale);
            }
        }
        return res;
    }

    /**
     * @return a proper {@link Locale} instance with language and country code set regardless of how
     * it was instantiated.  new Locale("pt-br") will be normalized to a locale constructed
     * with new Locale("pt","BR").
     */
    private SingletonLocale normalizeLocale(SingletonLocale singletonLocale) {
        SingletonLocale res = STRING_LOCALE_HASH_MAP.get(singletonLocale);
        if (res != null) {
            return res;
        }
        final String[] parts;
        final Locale locale = singletonLocale.getLocale();
        if (locale.getCountry().isEmpty()) {
            parts = LOCALE.split(locale.getLanguage());
        } else {
            parts = new String[] {locale.getLanguage(), locale.getCountry()};
        }

        if (parts.length == 1) {
            if ((res = SingletonLocale.get(Locale.forLanguageTag(parts[0]))) == null) {
                res = SingletonLocale.get(new Locale(parts[0]));
            }
        } else {
            res = SingletonLocale.get(new Locale(parts[0], parts[1]));
        }
        synchronized (FakerContext.class) {
            STRING_LOCALE_HASH_MAP.put(singletonLocale, res);
        }
        return res;
    }

    public void setCurrentLocale(Locale locale) {
        Objects.requireNonNull(locale);
        this.sLocale = normalizeLocale(SingletonLocale.get(locale));
        if (LOCALE_2_LOCALES_CHAIN.containsKey(this.sLocale)) {
            return;
        }
        synchronized (FakerContext.class) {
            LOCALE_2_LOCALES_CHAIN.put(this.sLocale, localeChain());
        }
    }

    /**
     * Convert the specified locale into a chain of locales used for message resolution. For example:
     * <p>
     * {@link Locale#FRANCE} (fr_FR) to [ fr_FR, anotherTest, en ]
     *
     * @return a list of {@link Locale} instances
     */
    protected List<SingletonLocale> localeChain(Locale from) {
        if (DEFAULT_LOCALE.getLocale().equals(from)) {
            return DEFAULT_SINGLETON_LOCALE_LIST;
        }

        return calculateLocaleChain(normalizeLocale(SingletonLocale.get(from)));
    }

    protected List<SingletonLocale> localeChain() {
        if (DEFAULT_LOCALE == sLocale) {
            return DEFAULT_SINGLETON_LOCALE_LIST;
        }

        return calculateLocaleChain(sLocale);
    }

    private List<SingletonLocale> calculateLocaleChain(SingletonLocale locale) {
        final List<SingletonLocale> chain = new ArrayList<>(3);
        chain.add(locale);
        if (!"".equals(locale.getLocale().getCountry())
            && !DEFAULT_LOCALE.getLocale().getLanguage().equals(locale.getLocale().getLanguage())) {
            chain.add(SingletonLocale.get(new Locale(locale.getLocale().getLanguage())));
        }
        chain.add(DEFAULT_LOCALE); // default
        return chain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FakerContext that = (FakerContext) o;

        if (!Objects.equals(sLocale, that.sLocale)) return false;
        return Objects.equals(randomService, that.randomService);
    }

    @Override
    public int hashCode() {
        int result = sLocale != null ? sLocale.hashCode() : 0;
        result = 31 * result + (randomService != null ? randomService.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FakerContext{" +
            ", locale=" + sLocale +
            ", randomService=" + randomService +
            '}';
    }
}
