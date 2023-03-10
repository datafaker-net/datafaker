package net.datafaker.service;

import net.datafaker.internal.helper.SingletonLocale;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FakeValues implements FakeValuesInterface {
    private static final Logger LOG = Logger.getLogger("faker");
    private final SingletonLocale sLocale;
    private final String filename;
    private final String path;
    private final Path filePath;
    private final URL url;
    private volatile Map<String, Object> values;
    private final Lock lock = new ReentrantLock();

    FakeValues(Locale locale) {
        this(locale, getFilename(locale), getFilename(locale), null, null);
    }

    FakeValues(Locale locale, Path filePath) {
        this(locale, getFilename(locale), null, filePath, null);
    }

    FakeValues(Locale locale, URL url) {
        this(locale, getFilename(locale), null, null, url);
    }

    FakeValues(Locale locale, String filename, String path) {
        this(locale, filename, path, null, null);
    }

    FakeValues(Locale locale, String filename, String path, Path filePath, URL url) {
        this.sLocale = SingletonLocale.get(locale);
        this.filename = filename;
        this.filePath = filePath;
        this.url = url;
        if (path == null) {
            lock.lock();
            try {
                if (values == null) {
                    values = loadValues();
                }
            } finally {
                lock.unlock();
            }
            this.path = values == null || values.isEmpty() ? null : values.keySet().iterator().next();
        } else {
            this.path = path;
        }
    }

    private static String getFilename(Locale locale) {
        final String lang = language(locale);
        if ("".equals(locale.getCountry())) {
            return lang;
        }
        return lang + "-" + locale.getCountry();
    }

    /**
     * If you new up a locale with "he", it gets converted to "iw" which is old.
     * This addresses that unfortunate condition.
     */
    private static String language(Locale l) {
        if (l.getLanguage().equals("iw")) {
            return "he";
        }
        return l.getLanguage();
    }

    @Override
    public Map<String, Object> get(String key) {
        if (values == null) {
            lock.lock();
            try {
                if (values == null) {
                    values = loadValues();
                }
            } finally {
                lock.unlock();
            }
        }

        return values == null ? null : (Map) values.get(key);
    }

    private Map<String, Object> loadFromFilePath() {
        if (filePath == null || !Files.exists(filePath) || Files.isDirectory(filePath) || !Files.isReadable(filePath)) {
            return null;
        }
        try (InputStream stream = Files.newInputStream(filePath)) {
            return readFromStream(stream);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Exception: ", e);
        }
        return null;
    }

    private Map<String, Object> loadFromUrl() {
        if (url == null) {
            return null;
        }
        try (InputStream stream = url.openStream()) {
            return readFromStream(stream);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception: ", e);
        }
        return null;
    }

    private Map<String, Object> loadValues() {
        Map<String, Object> result = loadFromFilePath();
        if (result != null) return result;
        result = loadFromUrl();
        if (result != null) return result;
        final Locale locale = sLocale.getLocale();
        final String[] paths = this.filename.isEmpty()
            ? new String[] {"/" + locale.getLanguage() + ".yml"}
            : new String[] {
                "/" + locale.getLanguage() + "/" + this.filename,
                "/" + filename + ".yml",
                "/" + locale.getLanguage() + ".yml"};

        for (String path : paths) {
            try (InputStream stream = getClass().getResourceAsStream(path)) {
                if (stream != null) {
                    result = readFromStream(stream);
                    enrichMapWithJavaNames(result);
                } else {
                    try (InputStream stream2 = getClass().getClassLoader().getResourceAsStream(path)) {
                        result = readFromStream(stream2);
                        enrichMapWithJavaNames(result);
                    } catch (Exception e) {
                        LOG.log(Level.SEVERE, "Exception: ", e);
                    }
                }

            } catch (IOException e) {
                LOG.log(Level.SEVERE, "Exception: ", e);
            }
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private void enrichMapWithJavaNames(Map<String, Object> result) {
        if (result != null) {
            Map<String, Object> map = null;
            for (Map.Entry<String, Object> entry : result.entrySet()) {
                final String key = entry.getKey();
                Object value = entry.getValue();
                if (entry.getValue() instanceof Map) {
                    Map<String, Object> nestedMap = new HashMap<>();
                    for (Map.Entry<String, Object> e: ((Map<String, Object>) entry.getValue()).entrySet()) {
                        nestedMap.put(toJavaNames(e.getKey(), true), e.getValue());
                    }
                    ((Map<String, Object>) entry.getValue()).putAll(nestedMap);
                }
                if (key.indexOf('_') != -1) {
                    if (map == null) {
                        map = new HashMap<>();
                    }
                    map.put(toJavaNames(key, false), value);
                }
            }
            if (map == null) {
                return;
            }
            result.putAll(map);
        }
    }

    private static String toJavaNames(String string, boolean isMethod) {
        final int length;
        if (string == null || (length = string.length()) == 0) {
            return string;
        }
        int cnt = 0;
        for (int i = 0; i < length; i++) {
            if (string.charAt(i) == '_') {
                cnt++;
            }
        }
        if (cnt == 0 && (Character.isUpperCase(string.charAt(0)) && !isMethod || isMethod && Character.isLowerCase(string.charAt(0)))) return string;
        final char[] res = new char[length - cnt];
        int pos = 0;
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            if (i == 0 && Character.isLetter(c)) {
                res[pos++] = isMethod ? Character.toLowerCase(c) : Character.toUpperCase(c);
            } else if (c == '_') {
                final char next = string.charAt(i + 1);
                if (i < length - 1 && Character.isLetter(next)) {
                    res[pos++] = Character.toUpperCase(next);
                    i++;
                }
            } else {
                res[pos++] = c;
            }
        }
        return new String(res);
    }

    private Map<String, Object> readFromStream(InputStream stream) {
        if (stream == null) return null;
        final Map<String, Object> valuesMap = new Yaml().loadAs(stream, Map.class);
        Map<String, Object> localeBased = (Map<String, Object>) valuesMap.get(sLocale.getLocale().getLanguage());
        if (localeBased == null) {
            localeBased = (Map<String, Object>) valuesMap.get(filename);
        }
        return (Map<String, Object>) localeBased.get("faker");
    }

    boolean supportsPath(String path) {
        return this.path.equals(path);
    }

    String getPath() {
        return path;
    }

    Locale getLocale() {
        return sLocale.getLocale();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FakeValues that = (FakeValues) o;
        return sLocale == that.sLocale && Objects.equals(filename, that.filename) && Objects.equals(path, that.path) && Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sLocale, filename, path, filePath);
    }
}
