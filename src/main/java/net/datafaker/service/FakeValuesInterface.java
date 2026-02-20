package net.datafaker.service;

import org.jspecify.annotations.Nullable;

import java.util.Map;

public interface FakeValuesInterface {
    @Nullable
    Map<String, Object> get(String key);
}
