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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    @ParameterizedTest
    @MethodSource("generateTestJson")
    public void simpleJsonTest(Map<Supplier<String>, Supplier<Object>> input, String expected) {
        Json json = new Json(input);
        assertEquals(expected, json.generate());
    }

    private static Stream<Arguments> generateTestJson() {
        return Stream.of(
                Arguments.of(map(entry(() -> "key", () -> "value")), "{\"key\": \"value\"}"),
                Arguments.of(map(entry(() -> "number", () -> 123)), "{\"number\": 123}"),
                Arguments.of(map(entry(() -> "number", () -> BigDecimal.valueOf(123.0))), "{\"number\": 123.0}"),
                Arguments.of(map(entry(() -> "number", () -> BigDecimal.valueOf(123.123))), "{\"number\": \"123.123\"}"),
                Arguments.of(map(entry(() -> "boolean", () -> true)), "{\"boolean\": true}"),
                Arguments.of(map(entry(() -> "nullValue", () -> null)), "{\"nullValue\": null}"),
                Arguments.of(map(entry(() -> "array", () -> new String[] {null, "test", "123"})), "{\"array\": [null, \"test\", \"123\"]}"),
                Arguments.of(map(entry(() -> "array", () -> new Integer[] {123, 456, 789})), "{\"array\": [123, 456, 789]}"),
                Arguments.of(map(entry(() -> "array", () -> new Object[] {"test", 456, true})), "{\"array\": [\"test\", 456, true]}"),
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

    private static Map<Supplier<String>, Supplier<Object>> map(Map.Entry<Supplier<String>, Supplier<Object>>... entries) {
        Map<Supplier<String>, Supplier<Object>> map = new LinkedHashMap<>();
        for (Map.Entry<Supplier<String>, Supplier<Object>> entry: entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
