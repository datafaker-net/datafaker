package net.datafaker.service;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
    private static final Map<FakeValuesContext, FakeValues> FAKE_VALUES_MAP = new HashMap<>();
    private final FakeValuesContext fakeValuesContext;
    private volatile Map<String, Object> values;
    private final Lock lock = new ReentrantLock();

    private FakeValues(FakeValuesContext fakeValuesContext) {
        this.fakeValuesContext = fakeValuesContext;
        if (fakeValuesContext.getPath() == null) {
            lock.lock();
            try {
                if (values == null) {
                    values = loadValues();
                }
            } finally {
                lock.unlock();
            }
            fakeValuesContext.setPath(values == null || values.isEmpty() ? null : values.keySet().iterator().next());
        }
    }

    public static FakeValues of(FakeValuesContext fakeValuesContext) {
        FakeValues fakeValues = FAKE_VALUES_MAP.get(fakeValuesContext);
        if (fakeValues != null) return fakeValues;
        synchronized (FakeValues.class) {
            fakeValues = FAKE_VALUES_MAP.get(fakeValuesContext);
            if (fakeValues != null) return fakeValues;
            fakeValues = new FakeValues(fakeValuesContext);
            FAKE_VALUES_MAP.put(fakeValuesContext, fakeValues);
            return fakeValues;
        }
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

    private Map<String, Object> loadFromUrl() {
        final URL url = fakeValuesContext.getUrl();
        if (url == null) {
            return null;
        }
        try (InputStream stream = url.openStream()) {
            return readFromStream(stream);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Exception: ", e);
        }
        return null;
    }

    private Map<String, Object> loadValues() {
        Map<String, Object> result = loadFromUrl();
        if (result != null) return result;
        result = loadFromUrl();
        if (result != null) return result;
        final Locale locale = fakeValuesContext.getLocale();
        final String fileName = fakeValuesContext.getFilename();
        final String[] paths = fileName.isEmpty()
            ? new String[] {"/" + locale.getLanguage() + ".yml"}
            : new String[] {
                "/" + locale.getLanguage() + "/" + fileName,
                "/" + fileName + ".yml",
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
        Map<String, Object> localeBased = (Map<String, Object>) valuesMap.get(fakeValuesContext.getLocale().getLanguage());
        if (localeBased == null) {
            localeBased = (Map<String, Object>) valuesMap.get(fakeValuesContext.getFilename());
        }
        return (Map<String, Object>) localeBased.get("faker");
    }

    boolean supportsPath(String path) {
        return fakeValuesContext.getPath().equals(path);
    }

    String getPath() {
        return fakeValuesContext.getPath();
    }

    Locale getLocale() {
        return fakeValuesContext.getLocale();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FakeValues that)) return false;
        return Objects.equals(fakeValuesContext, that.fakeValuesContext);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fakeValuesContext);
    }

    @Override
    public String toString() {
        return "FakeValues{" +
            "fakeValuesContext=" + fakeValuesContext +
            '}';
    }
}
