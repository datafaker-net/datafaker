package net.datafaker.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FakeValuesGrouping implements FakeValuesInterface {

    private final Map<String, Collection<FakeValues>> fakeValues = new HashMap<>();

    public void add(FakeValues fakeValue) {
        fakeValues.putIfAbsent(fakeValue.getPath(), new ArrayList<>());
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
}
