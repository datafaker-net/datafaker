package net.datafaker.service;

import net.datafaker.service.files.EnFile;

import java.util.*;

public class FakeValuesGrouping implements FakeValuesInterface {
    private static final FakeValuesGrouping ENGLISH_FAKE_VALUE_GROUPING = new FakeValuesGrouping();
    private final Map<String, Collection<FakeValues>> fakeValues = new HashMap<>();

    static {
        for (EnFile file : EnFile.getFiles()) {
            ENGLISH_FAKE_VALUE_GROUPING.add(new FakeValues(Locale.ENGLISH, file.getFile(), file.getPath()));
        }
    }

    public void add(FakeValues fakeValue) {
        fakeValues.putIfAbsent(fakeValue.getPath(), new HashSet<>());
        fakeValues.get(fakeValue.getPath()).add(fakeValue);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map get(String key) {
        Map result = null;
        for (FakeValues fakeValues : fakeValues.getOrDefault(key, Collections.emptyList())) {
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
