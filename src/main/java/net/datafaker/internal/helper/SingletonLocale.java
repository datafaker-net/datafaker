package net.datafaker.internal.helper;

import net.datafaker.service.FakerContext;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class allows to use {@link java.util.IdentityHashMap} for locales.
 * To do that it guarantees one instance of {@link SingletonLocale} per {@link Locale}.
 */
public class SingletonLocale {
    private static final Map<Locale, SingletonLocale> LOCALE2SINGLETON_LOCALE = new HashMap<>();
    private final Locale locale;

    // Hash code is required for FakerContext where SingletonLocale is a field
    private final int hashId = (int) System.nanoTime();

    private SingletonLocale(Locale locale) {
        this.locale = locale;
    }

    public static SingletonLocale get(Locale locale) {
        if (locale == null) {
            return null;
        }
        SingletonLocale res = LOCALE2SINGLETON_LOCALE.get(locale);
        if (res != null) {
            return res;
        }
        synchronized (SingletonLocale.class) {
            res = LOCALE2SINGLETON_LOCALE.get(locale);
            if (res != null) {
                return res;
            }
            res = new SingletonLocale(locale);
            LOCALE2SINGLETON_LOCALE.put(locale, res);
            return res;
        }
    }
    private static final Map<SingletonLocale, SingletonLocale> STRING_LOCALE_HASH_MAP = new IdentityHashMap<>();
    private static final Pattern LOCALE = Pattern.compile("[-_]");

    public static SingletonLocale normalizeLocale(SingletonLocale singletonLocale) {
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

    public Locale getLocale() {
        return locale;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return hashId;
    }
}
