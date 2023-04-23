package net.datafaker.internal.helper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

    @Override
    public String toString() {
        return "SingletonLocale{" +
            "locale=" + locale +
            '}';
    }
}
