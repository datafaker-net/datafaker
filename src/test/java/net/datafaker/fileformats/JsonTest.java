package net.datafaker.fileformats;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.fail;

class JsonTest {
    @ParameterizedTest
    @MethodSource("generateTestJson")
    void simpleJsonTest(Map<Object, Supplier<Object>> input, String expected) {
        Json.JsonBuilder builder = new Json.JsonBuilder();
        for (Map.Entry<Object, Supplier<Object>> entry: input.entrySet()) {
            if (entry.getKey() instanceof String) {
                builder.set((String) entry.getKey(), entry.getValue());
            } else if (entry.getKey() instanceof Supplier) {
                builder.set((Supplier<String>) entry.getKey(), entry.getValue());
            } else {
                fail("Key should be String or Supplier<String>");
            }
        }
        assertThat(builder.build().generate()).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestJson() {
        return Stream.of(
            Arguments.of(Collections.emptyMap(), "{}"),
            Arguments.of(map(entry(() -> "key", () -> new Json(map(entry(() -> "key", () -> "value"))))), "{\"key\": {\"key\": \"value\"}}"),
            Arguments.of(map(entry(() -> "key", () -> "value")), "{\"key\": \"value\"}"),
            Arguments.of(map(entry(() -> "number", () -> 123)), "{\"number\": 123}"),
            Arguments.of(map(entry(() -> "number", () -> BigDecimal.valueOf(123.0))), "{\"number\": 123.0}"),
            Arguments.of(map(entry(() -> "number", () -> BigDecimal.valueOf(123.123))), "{\"number\": \"123.123\"}"),
            Arguments.of(map(entry(() -> "boolean", () -> true)), "{\"boolean\": true}"),
            Arguments.of(map(entry(() -> "nullValue", () -> null)), "{\"nullValue\": null}"),
            Arguments.of(map(entry(() -> "array", () -> new String[]{null, "test", "123"})), "{\"array\": [null, \"test\", \"123\"]}"),
            Arguments.of(map(entry(() -> "array", () -> new Integer[]{123, 456, 789})), "{\"array\": [123, 456, 789]}"),
            Arguments.of(map(entry(() -> "array", () -> new Object[]{"test", 456, true})), "{\"array\": [\"test\", 456, true]}"),
            Arguments.of(map(entry(() -> "emptyarray", () -> new Long[]{})), "{\"emptyarray\": []}"),
            Arguments.of(map(entry(() -> "emptyarray", Collections::emptyList)), "{\"emptyarray\": []}"),
            Arguments.of(map(entry(() -> "es\"ca\"ped", () -> "va\"lu\"e")), "{\"es\\\"ca\\\"ped\": \"va\\\"lu\\\"e\"}"),
            Arguments.of(map(entry(() -> "key", () -> "value"), entry(() -> "nested", () -> map(entry(() -> "nestedkey", () -> "nestedvalue")))),
                "{\"key\": \"value\", \"nested\": {\"nestedkey\": \"nestedvalue\"}}")
        );
    }

    private static Map.Entry<Supplier<String>, Supplier<Object>> entry(Supplier<String> key, Supplier<Object> value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    @SafeVarargs
    private static Map<Supplier<String>, Supplier<Object>> map(Map.Entry<Supplier<String>, Supplier<Object>>... entries) {
        Map<Supplier<String>, Supplier<Object>> map = new LinkedHashMap<>();
        for (Map.Entry<Supplier<String>, Supplier<Object>> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
