package net.datafaker.service;

import net.datafaker.annotations.InternalApi;
import net.datafaker.internal.helper.SingletonLocale;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;

class FakeValuesContext {
    private final SingletonLocale singletonLocale;
    private final String filename;
    private final int filenameHashCode;
    private final String path;
    private final URL url;
    private final int urlHashCode;

    private FakeValuesContext(Locale locale) {
        this(locale, getFilename(locale), getFilename(locale), null);
    }

    private FakeValuesContext(Locale locale, URL url) {
        this(locale, getFilename(locale), null, url);
    }

    private FakeValuesContext(Locale locale, String filename, String path) {
        this(locale, filename, path, null);
    }

    private FakeValuesContext(Locale locale, String filename, String path, URL url) {
        this.singletonLocale = SingletonLocale.get(locale);
        this.filename = filename;
        this.path = path;
        this.url = url;
        this.filenameHashCode = filename == null ? 0 : filename.hashCode();
        try {
            this.urlHashCode = url == null ? 0 : url.toURI().hashCode();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid url: \"%s\"".formatted(url), e);
        }
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
        return new FakeValuesContext(locale, filename, path, url);
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

    public Locale getLocale() {
        return singletonLocale.getLocale();
    }

    public String getFilename() {
        return filename;
    }

    @InternalApi
    String getPath() {
        return path;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FakeValuesContext that = (FakeValuesContext) o;

        if (!Objects.equals(singletonLocale, that.singletonLocale)) return false;
        if (!Objects.equals(filename, that.filename)) return false;
        if (!Objects.equals(path, that.path)) return false;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        int result = singletonLocale == null ? 0 : singletonLocale.hashCode();
        result = 31 * result + filenameHashCode;
        result = 31 * result + (path == null ? 0 : path.hashCode());
        result = 31 * result + urlHashCode;
        return result;
    }

    @Override
    public String toString() {
        return "FakeValuesContext{%s, filename='%s', path='%s', url=%s}".formatted(singletonLocale, filename, path, url);
    }
}
