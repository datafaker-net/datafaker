package net.datafaker.internal.helper;

import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class allows to use {@link java.util.IdentityHashMap} for locales.
 * To do that it guarantees one instance of {@link SingletonLocale} per {@link Locale}.
 */
public class SingletonLocale {
    private static final Map<Locale, SingletonLocale> LOCALE2SINGLETON_LOCALE = new ConcurrentHashMap<>();
    private final Locale locale;

    // Hash code is required for FakerContext where SingletonLocale is a field
    private final int hashId = (int) System.nanoTime();

    private SingletonLocale(Locale locale) {
        this.locale = locale;
    }

    @Nullable
    public static SingletonLocale get(@Nullable Locale locale) {
        if (locale == null) {
            return null;
        }
        return getRequired(locale);
    }

    public static SingletonLocale getRequired(Locale locale) {
        return LOCALE2SINGLETON_LOCALE.computeIfAbsent(locale, (__) -> new SingletonLocale(locale));
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
        return "SingletonLocale{%s}".formatted(locale);
    }
}
