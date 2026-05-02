package net.datafaker.service;

import net.datafaker.internal.helper.LazyEvaluated;
import net.datafaker.internal.helper.WordUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.emptyMap;
import static net.datafaker.internal.helper.JavaNames.toJavaNames;

public class FakeValues implements FakeValuesInterface {
    private static final Map<FakeValuesContext, FakeValues> FAKE_VALUES_MAP = new ConcurrentHashMap<>();
    private final FakeValuesContext fakeValuesContext;
    private final LazyEvaluated<Map<String, Object>> values = new LazyEvaluated<>(() -> loadValues());

    private FakeValues(FakeValuesContext fakeValuesContext) {
        this.fakeValuesContext = fakeValuesContext;
    }

    static FakeValues of(FakeValuesContext fakeValuesContext) {
        return FAKE_VALUES_MAP.computeIfAbsent(fakeValuesContext, FakeValues::new);
    }

    @Override
    public Map<String, Object> get(String key) {
        return getMap(values.get(), key);
    }

    private Map<String, Object> loadFromUrl() {
        final URL url = fakeValuesContext.getUrl();
        if (url == null) {
            return null;
        }
        try (InputStream stream = url.openStream()) {
            Map<String, Object> result = readFromStream(stream);
            enrichMapWithJavaNames(result);
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read fake values from %s".formatted(url), e);
        }
    }

    private Map<String, Object> loadValues() {
        Map<String, Object> result = loadFromUrl();
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
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException("Failed to read fake values from %s".formatted(path), e);
            }
            if (result != null) {
                return result;
            }
        }
        return emptyMap();
    }

    private void enrichMapWithJavaNames(Map<String, Object> result) {
        if (result != null) {
            Map<String, Object> map = null;
            for (Map.Entry<String, Object> entry : result.entrySet()) {
                final String key = entry.getKey();
                Object value = entry.getValue();
                if (entry.getValue() instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> entryMap = (Map<String, Object>) entry.getValue();
                    prefixUnqualifiedExpressions(entryMap, key);
                    Map<String, Object> nestedMap = new HashMap<>(entryMap.size());
                    for (Map.Entry<String, Object> e: entryMap.entrySet()) {
                        nestedMap.put(toJavaNames(e.getKey(), true), e.getValue());
                    }
                    entryMap.putAll(nestedMap);
                }
                // indexOf(<String>) is faster than indexOf(<char>) since it has jvm intrinsic
                if (key.contains("_")) {
                    if (map == null) {
                        map = new HashMap<>();
                    }
                    map.put(toJavaNames(key, false), value);
                }
            }
            if (map != null) {
                result.putAll(map);
            }
        }
    }

    /**
     * Rewrites unqualified expressions like {@code #{first_name}} inside string lists under a
     * provider section into qualified ones like {@code #{Name.firstName}}. The provider name comes
     * from the top-level YAML key (e.g. {@code name}, {@code address}). Doing this once at load
     * time avoids racy in-place mutation of cached lists during concurrent fetches.
     */
    static void prefixUnqualifiedExpressions(Object node, String providerKey) {
        if (node instanceof Map<?, ?> nested) {
            for (Object v : nested.values()) {
                prefixUnqualifiedExpressions(v, providerKey);
            }
        } else if (node instanceof List<?> rawList) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) rawList;
            rewriteList(list, providerKey);
        }
    }

    private static void rewriteList(List<Object> list, String providerKey) {
        final String capitalizedProvider = WordUtils.capitalize(providerKey);
        for (int i = 0; i < list.size(); i++) {
            Object itemValue = list.get(i);
            if (!(itemValue instanceof String item)) {
                break;
            }
            final int itemLength = item.length();
            if (itemLength < 2) {
                break;
            }
            int j = 0;
            StringBuilder sb = null;
            int start = 0;
            while (j < itemLength) {
                char c;
                while (j < itemLength - 2 && !(item.charAt(j) == '#' && item.charAt(j + 1) == '{')) {
                    j++;
                }
                int startWord = j + 2;
                boolean letterOrDigitOnly = true;
                j = startWord;
                while (j < itemLength && (c = item.charAt(j)) != '}') {
                    letterOrDigitOnly &= Character.isLetter(c) || Character.isDigit(c) || c == '_';
                    j++;
                }
                if (start < itemLength && startWord < itemLength && letterOrDigitOnly) {
                    if (sb == null) {
                        sb = new StringBuilder();
                    }
                    sb.append(item, start, startWord);
                    sb.append(capitalizedProvider).append(".").append(toJavaNames(item.substring(startWord, j), true)).append("}");
                    start = j + 1;
                }
            }
            if (sb != null) {
                if (start < itemLength) {
                    sb.append(item, start, itemLength);
                }
                list.set(i, sb.toString());
            }
        }
    }

    private Map<String, Object> readFromStream(InputStream stream) {
        if (stream == null) return null;
        final Map<String, Object> valuesMap = new Yaml().loadAs(stream, Map.class);
        Map<String, Object> localeBased = getMap(valuesMap, fakeValuesContext.getLocale().getLanguage());
        if (localeBased == null) {
            localeBased = getMap(valuesMap, fakeValuesContext.getFilename());
        }
        return getMap(localeBased, "faker");
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> getMap(Map<String, Object> map, String key) {
        return (Map<String, Object>) map.get(key);
    }

    Set<String> getPaths() {
        return fakeValuesContext.getPath() != null ?
            Set.of(fakeValuesContext.getPath()) :
            keysOf(values.get());
    }

    private static Set<String> keysOf(Map<String, ?> map) {
        return map == null || map.isEmpty() ? null : map.keySet();
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
        return "FakeValues{%s}".formatted(fakeValuesContext);
    }
}
