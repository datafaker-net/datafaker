package net.datafaker.service;

import net.datafaker.service.files.EnFile;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

public class FakeValuesGrouping implements FakeValuesInterface {
    private static final FakeValuesGrouping ENGLISH_FAKE_VALUE_GROUPING = new FakeValuesGrouping();
    private final Map<String, Collection<FakeValuesInterface>> fakeValues = new HashMap<>();

    static {
        for (EnFile file : EnFile.getFiles()) {
            ENGLISH_FAKE_VALUE_GROUPING.add(new FakeValues(Locale.ENGLISH, file.getFile(), file.getPath()));
        }
    }

    public void add(FakeValuesInterface fakeValue) {
        if (fakeValue instanceof FakeValues) {
            fakeValues.computeIfAbsent(((FakeValues) fakeValue).getPath(), key -> new HashSet<>())
                .add(fakeValue);
        } else if (fakeValue instanceof FakeValuesGrouping) {
            fakeValues.putAll(((FakeValuesGrouping) fakeValue).fakeValues);
        } else {
            throw new RuntimeException(fakeValues.getClass() + " not supported (please raise an issue)");
        }
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map get(String key) {
        Map result = null;
        for (FakeValuesInterface fakeValues : fakeValues.getOrDefault(key, Collections.emptyList())) {
            if (result != null) {
                final Map newResult = fakeValues.get(key);
                result.putAll(newResult);
            } else {
                result = fakeValues.get(key);
            }
        }
        return result;
    }

    public static FakeValuesGrouping getEnglishFakeValueGrouping() {
        return ENGLISH_FAKE_VALUE_GROUPING;
    }
}
