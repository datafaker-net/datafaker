package net.datafaker.formats;

import net.datafaker.transformations.Field;
import net.datafaker.transformations.JsonTransformer;
import net.datafaker.transformations.Schema;
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

import static net.datafaker.transformations.Field.compositeField;
import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.of;

class JsonTest {

    @ParameterizedTest
    @MethodSource("generateTestSchema")
    void simpleJsonTestForJsonTransformer(Schema<String, String> schema, String expected) {
        JsonTransformer<String> transformer = new JsonTransformer<>();
        assertThat(transformer.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestSchema() {
        return Stream.of(
            of(Schema.of(), "{}"),
            of(
                Schema.of(compositeField("key", new Field[]{field("key", () -> "value")})),
                "{\"key\": {\"key\": \"value\"}}"),
            of(Schema.of(field("key", () -> "value")), "{\"key\": \"value\"}"),
            of(Schema.of(field("number", () -> 123)), "{\"number\": 123}"),
            of(Schema.of(field("number", () -> 123.0)), "{\"number\": 123.0}"),
            of(Schema.of(field("number", () -> 123.123)), "{\"number\": \"123.123\"}"),
            of(Schema.of(field("boolean", () -> true)), "{\"boolean\": true}"),
            of(Schema.of(field("nullValue", () -> null)), "{\"nullValue\": null}"),
            of(
                Schema.of(field("array", () -> new String[]{null, "test", "123"})),
                "{\"array\": [null, \"test\", \"123\"]}"),
            of(
                Schema.of(field("array", () -> new Integer[]{123, 456, 789})),
                "{\"array\": [123, 456, 789]}"),
            of(
                Schema.of(field("array", () -> new Object[]{"test", 456, true})),
                "{\"array\": [\"test\", 456, true]}"),
            of(Schema.of(field("emptyarray", () -> new Long[]{})), "{\"emptyarray\": []}"),
            of(Schema.of(field("emptyarray", Collections::emptyList)), "{\"emptyarray\": []}"),
            of(
                Schema.of(field("es\"ca\"ped", () -> "va\"lu\"e")),
                "{\"es\\\"ca\\\"ped\": \"va\\\"lu\\\"e\"}"),
            of(
                Schema.of(
                    field("key", () -> "value"),
                    compositeField("nested", new Field[]{field("nestedkey", () -> "nestedvalue")})),
                "{\"key\": \"value\", \"nested\": {\"nestedkey\": \"nestedvalue\"}}"));
    }

    @ParameterizedTest
    @MethodSource("generateTestJson")
    void simpleJsonTest(Map<Object, Supplier<Object>> input, String expected) {
        Json.JsonBuilder builder = new Json.JsonBuilder();
        for (Map.Entry<Object, Supplier<Object>> entry : input.entrySet()) {
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
            of(Collections.emptyMap(), "{}"),
            of(
                map(entry(() -> "key", () -> new Json(map(entry(() -> "key", () -> "value"))))),
                "{\"key\": {\"key\": \"value\"}}"),
            of(map(entry(() -> "key", () -> "value")), "{\"key\": \"value\"}"),
            of(map(entry(() -> "number", () -> 123)), "{\"number\": 123}"),
            of(map(entry(() -> "number", () -> BigDecimal.valueOf(123.0))), "{\"number\": 123.0}"),
            of(
                map(entry(() -> "number", () -> BigDecimal.valueOf(123.123))),
                "{\"number\": \"123.123\"}"),
            of(map(entry(() -> "boolean", () -> true)), "{\"boolean\": true}"),
            of(map(entry(() -> "nullValue", () -> null)), "{\"nullValue\": null}"),
            of(
                map(entry(() -> "array", () -> new String[]{null, "test", "123"})),
                "{\"array\": [null, \"test\", \"123\"]}"),
            of(
                map(entry(() -> "array", () -> new Integer[]{123, 456, 789})),
                "{\"array\": [123, 456, 789]}"),
            of(
                map(entry(() -> "array", () -> new Object[]{"test", 456, true})),
                "{\"array\": [\"test\", 456, true]}"),
            of(map(entry(() -> "emptyarray", () -> new Long[]{})), "{\"emptyarray\": []}"),
            of(map(entry(() -> "emptyarray", Collections::emptyList)), "{\"emptyarray\": []}"),
            of(
                map(entry(() -> "es\"ca\"ped", () -> "va\"lu\"e")),
                "{\"es\\\"ca\\\"ped\": \"va\\\"lu\\\"e\"}"),
            of(
                map(
                    entry(() -> "key", () -> "value"),
                    entry(() -> "nested", () -> map(entry(() -> "nestedkey", () -> "nestedvalue")))),
                "{\"key\": \"value\", \"nested\": {\"nestedkey\": \"nestedvalue\"}}"));
    }

    private static Map.Entry<Supplier<String>, Supplier<Object>> entry(
        Supplier<String> key, Supplier<Object> value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    @SafeVarargs
    private static Map<Supplier<String>, Supplier<Object>> map(
        Map.Entry<Supplier<String>, Supplier<Object>>... entries) {
        Map<Supplier<String>, Supplier<Object>> map = new LinkedHashMap<>();
        for (Map.Entry<Supplier<String>, Supplier<Object>> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
