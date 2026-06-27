package net.datafaker.service;

import net.datafaker.internal.helper.SingletonLocale;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;

record FakeValuesContext(SingletonLocale singletonLocale, String filename, String path, URL url) {

    private FakeValuesContext(Locale locale) {
        this(SingletonLocale.get(locale), getFilename(locale), getFilename(locale), null);
    }

    private FakeValuesContext(Locale locale, URL url) {
        this(SingletonLocale.get(locale), getFilename(locale), null, url);
    }

    private FakeValuesContext(Locale locale, String filename, String path) {
        this(SingletonLocale.get(locale), filename, path, null);
    }

    public static FakeValuesContext of(Locale locale) {
        return new FakeValuesContext(locale);
    }

    public static FakeValuesContext of(Locale locale, URL url) {
        return new FakeValuesContext(locale, url);
    }

    public static FakeValuesContext of(Locale locale, String filename, String path) {
        return new FakeValuesContext(locale, filename, path);
    }

    public static FakeValuesContext of(Locale locale, String filename, String path, URL url) {
        return new FakeValuesContext(SingletonLocale.get(locale), filename, path, url);
    }

    private static String getFilename(Locale locale) {
        String lang = language(locale);
        if (locale.getCountry().isEmpty()) {
            return lang;
        } else if (locale.getLanguage().isEmpty()) {
            return "_" + locale.getCountry();
        } else {
            return lang + "-" + locale.getCountry();
        }
    }

    /**
     * If you create a locale with "he", it gets converted to "iw" which is old.
     * This addresses that unfortunate condition.
     */
    private static String language(Locale locale) {
        return switch (locale.getLanguage()) {
            case "iw" -> "he";
            case "in" -> "id";
            case "ji" -> "yi";
            default -> locale.getLanguage();
        };
    }

    Locale getLocale() {
        return singletonLocale.getLocale();
    }

    String getFilename() {
        return filename;
    }

    String getPath() {
        return path;
    }

    URL getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FakeValuesContext that)) {
            return false;
        }
        return Objects.equals(singletonLocale, that.singletonLocale)
            && Objects.equals(filename, that.filename)
            && Objects.equals(path, that.path)
            && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        int result = singletonLocale == null ? 0 : singletonLocale.hashCode();
        result = 31 * result + (filename == null ? 0 : filename.hashCode());
        result = 31 * result + (path == null ? 0 : path.hashCode());
        result = 31 * result + urlHashCode(url);
        return result;
    }

    private static int urlHashCode(URL url) {
        if (url == null) {
            return 0;
        }
        try {
            return url.toURI().hashCode();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid url: \"%s\"".formatted(url), e);
        }
    }

    @Override
    public String toString() {
        return "FakeValuesContext{%s, filename='%s', path='%s', url=%s}".formatted(singletonLocale, filename, path, url);
    }
}
