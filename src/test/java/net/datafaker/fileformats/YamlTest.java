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

class YamlTest {
    @ParameterizedTest
    @MethodSource("generateTestYaml")
    void simpleYamlTest(Map<Supplier<String>, Supplier<Object>> input, String expected) {
        Yaml yaml = new Yaml(input);
        assertThat(yaml.generate()).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestYaml() {
        return Stream.of(
            Arguments.of(map(entry(() -> "key", () -> "value")), "key: value" + System.lineSeparator()),
            Arguments.of(map(entry(() -> "number", () -> 123)), "number: 123" + System.lineSeparator()),
            Arguments.of(map(entry(() -> "number", () -> BigDecimal.valueOf(123.0))), "number: 123.0" + System.lineSeparator()),
            Arguments.of(map(entry(() -> "number", () -> BigDecimal.valueOf(123.123))), "number: 123.123" + System.lineSeparator()),
            Arguments.of(map(entry(() -> "boolean", () -> true)), "boolean: true" + System.lineSeparator()),
            Arguments.of(map(entry(() -> "nullValue", () -> null)), "nullValue: null" + System.lineSeparator()),
            Arguments.of(map(entry(() -> "array", () -> new String[]{null, "test", "123"})),
                "array:" + System.lineSeparator()
                    + "  - null" + System.lineSeparator()
                    + "  - test" + System.lineSeparator()
                    + "  - 123" + System.lineSeparator()),
            Arguments.of(map(entry(() -> "array", () -> new Integer[]{123, 456, 789})),
                "array:" + System.lineSeparator()
                    + "  - 123" + System.lineSeparator()
                    + "  - 456" + System.lineSeparator()
                    + "  - 789" + System.lineSeparator()),
            Arguments.of(map(entry(() -> "array", () -> new Object[]{"test", 456, true})),
                "array:" + System.lineSeparator()
                    + "  - test" + System.lineSeparator()
                    + "  - 456" + System.lineSeparator()
                    + "  - true" + System.lineSeparator()),
            Arguments.of(map(entry(() -> "emptyarray", () -> new Long[]{})), "emptyarray:" + System.lineSeparator()),
            Arguments.of(map(entry(() -> "emptyarray", Collections::emptyList)), "emptyarray:" + System.lineSeparator()),
            Arguments.of(map(entry(() -> "key", () -> "value"),
                    entry(() -> "nested", () -> map(entry(() -> "nestedkey", () -> "nestedvalue")))),
                "key: value" + System.lineSeparator() + "nested:" + System.lineSeparator() + "  nestedkey: nestedvalue" + System.lineSeparator()),
            Arguments.of(map(entry(() -> "key", () -> "value"),
                    entry(() -> "nested",
                        () -> map(entry(() -> "nestedkey", () -> "nestedvalue"),
                            entry(() -> "nested2", () -> map(entry(() -> "nestedkey2", () -> "nestedvalue2")))))),
                "key: value" + System.lineSeparator()
                    + "nested:" + System.lineSeparator() + "  nestedkey: nestedvalue" + System.lineSeparator()
                    + "  nested2:" + System.lineSeparator()
                    + "    nestedkey2: nestedvalue2" + System.lineSeparator())
        );
    }

    private static Map.Entry<Supplier<String>, Supplier<Object>> entry(Supplier<String> key, Supplier<Object> value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    private static Map<Supplier<String>, Supplier<Object>> map(Map.Entry<Supplier<String>, Supplier<Object>>... entries) {
        Map<Supplier<String>, Supplier<Object>> map = new LinkedHashMap<>();
        for (Map.Entry<Supplier<String>, Supplier<Object>> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
