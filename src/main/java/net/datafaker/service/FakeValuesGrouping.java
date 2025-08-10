package net.datafaker.service;

import net.datafaker.service.files.EnFile;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
        if (fakeValue instanceof FakeValues) {
            ((FakeValues) fakeValue).getPaths().forEach(p ->
            fakeValues.computeIfAbsent(p, key -> new HashSet<>())
                .add(fakeValue));
        } else if (fakeValue instanceof FakeValuesGrouping) {
            fakeValues.putAll(((FakeValuesGrouping) fakeValue).fakeValues);
        } else {
            throw new RuntimeException(fakeValues.getClass().getName() + " not supported (please raise an issue)");
        }
    }

    @Override
    public Map<String, Object> get(String key) {
        Map<String, Object> result = null;
        for (FakeValuesInterface fakeValues : fakeValues.getOrDefault(key, emptyList())) {
            if (result == null) {
                result = fakeValues.get(key);
            } else {
                final Map<String, Object> newResult = fakeValues.get(key);
                result.putAll(newResult);
            }
        }
        return result;
    }

    public static FakeValuesGrouping getEnglishFakeValueGrouping() {
        return ENGLISH_FAKE_VALUE_GROUPING;
    }
}
