package net.datafaker.service;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FakeValues implements FakeValuesInterface {
    private static final Logger LOG = Logger.getLogger("faker");
    private final Locale locale;
    private final String filename;
    private final String path;
    private final Path filePath;
    private volatile Map<String, Object> values;
    private final Lock lock = new ReentrantLock();

    FakeValues(Locale locale) {
        this(locale, getFilename(locale), getFilename(locale), null);
    }

    FakeValues(Locale locale, Path filePath) {
        this(locale, getFilename(locale), null, filePath);
    }

    FakeValues(Locale locale, String filename, String path) {
        this(locale, filename, path, null);
    }

    FakeValues(Locale locale, String filename, String path, Path filePath) {
        this.locale = locale;
        this.filename = filename;
        this.filePath = filePath;
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

    private Map<String, Object> loadValues() {
        Map<String, Object> result = loadFromFilePath();
        if (result != null) return result;
        String pathWithLocaleAndFilename = "/" + locale.getLanguage() + "/" + this.filename;
        String pathWithFilename = "/" + filename + ".yml";
        String pathWithLocale = "/" + locale.getLanguage() + ".yml";

        List<String> paths = Arrays.asList(pathWithLocaleAndFilename, pathWithFilename, pathWithLocale);
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
            Set<String> set = new HashSet<>();
            for (Map.Entry<String, Object> entry : result.entrySet()) {
                final String key = entry.getKey();
                if (key.indexOf('_') != -1) {
                    set.add(key);
                }
            }
            for (String str : set) {
                result.put(toJavaNames(str), result.get(str));
            }
        }
    }

    private static String toJavaNames(String string) {
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
        if (cnt == 0 && Character.isUpperCase(string.charAt(0))) return string;
        final char[] res = new char[length - cnt];
        int pos = 0;
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            if (i == 0 && Character.isLetter(c)) {
                res[pos++] = Character.toUpperCase(c);
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
        Map<String, Object> localeBased = (Map<String, Object>) valuesMap.get(locale.getLanguage());
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
        return locale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FakeValues that = (FakeValues) o;
        return Objects.equals(locale, that.locale) && Objects.equals(filename, that.filename) && Objects.equals(path, that.path) && Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locale, filename, path, filePath);
    }
}
