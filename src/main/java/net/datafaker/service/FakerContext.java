package net.datafaker.service;

import net.datafaker.internal.helper.SingletonLocale;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import static java.util.Locale.ROOT;
import static java.util.Objects.requireNonNull;
import static net.datafaker.service.FakeValuesService.DEFAULT_LOCALE;

/**
 * Contains the changeable Faker's part which currently contains {@link Locale} and {@link RandomService}.
 */
public class FakerContext {
    private static final Pattern LOCALE = Pattern.compile("[-_]");
    private static final Map<SingletonLocale, List<SingletonLocale>> LOCALE_2_LOCALES_CHAIN = new ConcurrentHashMap<>();
    private static final Map<SingletonLocale, SingletonLocale> STRING_LOCALE_HASH_MAP = new ConcurrentHashMap<>();
    private static final List<SingletonLocale> DEFAULT_SINGLETON_LOCALE_LIST = List.of(DEFAULT_LOCALE);
    private static final Map<String, String> LANGUAGE_DEFAULT_COUNTRY = Map.ofEntries(
        Map.entry("be", "BY"),
        Map.entry("cs", "CZ"),
        Map.entry("da", "DK"),
        Map.entry("el", "GR"),
        Map.entry("et", "EE"),
        Map.entry("he", "IL"),
        Map.entry("hy", "AM"),
        Map.entry("ja", "JP"),
        Map.entry("ka", "GE"),
        Map.entry("ko", "KR"),
        Map.entry("nb", "NO"),
        Map.entry("sq", "AL"),
        Map.entry("sv", "SE"),
        Map.entry("ta", "IN"),
        Map.entry("uk", "UA"),
        Map.entry("vi", "VN"),
        Map.entry("zh", "CN")
    );

    private volatile SingletonLocale sLocale;
    private volatile RandomService randomService;


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
        this.randomService = randomService;
        setCurrentLocale(locale);
    }

    public final synchronized void setLocale(Locale locale) {
        this.sLocale = singletonLocale(locale);
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
        SingletonLocale locale = sLocale;
        List<SingletonLocale> chain = LOCALE_2_LOCALES_CHAIN.get(locale);
        return chain == null ? calculateLocaleChain(locale) : chain;
    }

    /**
     * @return a proper {@link Locale} instance with language and country code set regardless of how
     * it was instantiated.  new Locale("pt-br") will be normalized to a locale constructed
     * with new Locale("pt","BR").
     */
    private SingletonLocale normalizeLocale(SingletonLocale singletonLocale) {
        return STRING_LOCALE_HASH_MAP.computeIfAbsent(singletonLocale, FakerContext::normalizeUncachedLocale);
    }

    private static SingletonLocale normalizeUncachedLocale(SingletonLocale singletonLocale) {
        final String[] parts;
        final Locale locale = singletonLocale.getLocale();
        if (locale.getCountry().isEmpty()) {
            parts = LOCALE.split(locale.getLanguage());
        } else {
            parts = new String[] {locale.getLanguage(), locale.getCountry()};
        }

        if (parts.length == 1) {
            return SingletonLocale.getRequired(Locale.forLanguageTag(parts[0]));
        }
        return SingletonLocale.getRequired(new Locale(parts[0], parts[1]));
    }

    public final synchronized void setCurrentLocale(Locale locale) {
        requireNonNull(locale);
        SingletonLocale normalized = normalizeLocale(singletonLocale(locale));
        this.sLocale = normalized;
        if (!LOCALE_2_LOCALES_CHAIN.containsKey(normalized)) {
            LOCALE_2_LOCALES_CHAIN.putIfAbsent(normalized, List.copyOf(localeChain()));
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

        return calculateLocaleChain(normalizeLocale(singletonLocale(from)));
    }

    protected List<SingletonLocale> localeChain() {
        return calculateLocaleChain(sLocale);
    }

    private List<SingletonLocale> calculateLocaleChain(SingletonLocale locale) {
        if (DEFAULT_LOCALE == locale) {
            return DEFAULT_SINGLETON_LOCALE_LIST;
        }
        final List<SingletonLocale> chain = new ArrayList<>(4);
        chain.add(locale);
        if (locale.getLocale().getCountry().isEmpty()) {
            chain.add(SingletonLocale.get(new Locale("", defaultCountryForLanguage(locale.getLocale().getLanguage()))));
        } else {
            if (!DEFAULT_LOCALE.getLocale().getLanguage().equals(locale.getLocale().getLanguage())) {
                chain.add(SingletonLocale.get(new Locale(locale.getLocale().getLanguage())));
            }
            chain.add(SingletonLocale.get(new Locale("", locale.getLocale().getCountry())));
        }
        chain.add(DEFAULT_LOCALE);
        return chain;
    }

    private String defaultCountryForLanguage(String language) {
        return LANGUAGE_DEFAULT_COUNTRY.getOrDefault(language, language.toUpperCase(ROOT));
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
        return "FakerContext{%s, %s}".formatted(sLocale, randomService);
    }

    private static SingletonLocale singletonLocale(Locale locale) {
        return requireNonNull(SingletonLocale.get(locale), () -> "Unsupported locale " + locale);
    }
}
