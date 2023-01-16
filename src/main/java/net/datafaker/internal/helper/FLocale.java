package net.datafaker.internal.helper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FLocale {
    private static final Map<Locale, FLocale> LOCALE2FLOCALE = new HashMap<>();
    private final Locale locale;
    private final int hashId = (int) System.nanoTime();

    private FLocale(Locale locale) {
        this.locale = locale;
    }

    public static FLocale get(Locale locale) {
        if (locale == null) {
            return null;
        }
        FLocale res = LOCALE2FLOCALE.get(locale);
        if (res != null) {
            return res;
        }
        synchronized (FLocale.class) {
            res = LOCALE2FLOCALE.get(locale);
            if (res != null) {
                return res;
            }
            res = new FLocale(locale);
            LOCALE2FLOCALE.put(locale, res);
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
}
