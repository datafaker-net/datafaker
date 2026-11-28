package net.datafaker.service;

import net.datafaker.service.files.EnFile;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;

import static java.util.Collections.emptyList;

public final class FakeValuesGrouping implements FakeValuesInterface {
    private static final FakeValuesGrouping ENGLISH_FAKE_VALUE_GROUPING = new FakeValuesGrouping();
    private final Map<String, Collection<FakeValuesInterface>> fakeValues = new HashMap<>();

    static {
        EnFile.getFiles().forEach(file -> {
            ENGLISH_FAKE_VALUE_GROUPING.add(FakeValues.of(FakeValuesContext.of(Locale.ENGLISH, file.getFile(), file.getPath())));
        });
    }

    public FakeValuesGrouping() {
    }

    public FakeValuesGrouping(FakeValues values) {
        add(values);
    }

    public void add(FakeValuesInterface fakeValue) {
        if (fakeValue instanceof FakeValues values) {
            values.getPaths().forEach(p ->
                fakeValues.computeIfAbsent(p, key -> new LinkedHashSet<>()).add(fakeValue));
        } else if (fakeValue instanceof FakeValuesGrouping grouping) {
            grouping.fakeValues.forEach((key, values) -> fakeValues.put(key, new LinkedHashSet<>(values)));
        } else {
            throw new RuntimeException(fakeValues.getClass().getName() + " not supported (please raise an issue)");
        }
    }

    @Nullable
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map<String, Object> get(String key) {
        Map<String, Object> result = null;
        boolean merged = false;
        for (FakeValuesInterface fakeValues : fakeValues.getOrDefault(key, emptyList())) {
            Map<String, Object> values = fakeValues.get(key);
            if (result == null) {
                result = values;
            } else if (values != null) {
                if (!merged) {
                    result = new HashMap<>(result);
                    merged = true;
                }
                result.putAll(values);
            }
        }
        return result;
    }

    public static FakeValuesGrouping getEnglishFakeValueGrouping() {
        return ENGLISH_FAKE_VALUE_GROUPING;
    }
}
