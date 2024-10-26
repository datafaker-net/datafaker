package net.datafaker.formats;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Name;
import net.datafaker.transformations.Schema;
import net.datafaker.transformations.YamlTransformer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class YamlTest {

    @ParameterizedTest
    @MethodSource("generateTestSchema")
    void simpleYamlTest(Schema<String, String> schema, String expected) {
        YamlTransformer<String> yamlTransformer = new YamlTransformer<>();
        assertThat(yamlTransformer.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestSchema() {
        return Stream.of(
            of(Schema.of(), ""),
            of(Schema.of(field("key", () -> "value")), "key: value" + System.lineSeparator()),
            of(Schema.of(field("number", () -> 123)), "number: 123" + System.lineSeparator()),
            of(Schema.of(field("number", () -> BigDecimal.valueOf(123.0))), "number: 123.0" + System.lineSeparator()),
            of(Schema.of(field("number", () -> BigDecimal.valueOf(123.123))), "number: 123.123" + System.lineSeparator()),
            of(Schema.of(field("boolean", () -> true)), "boolean: true" + System.lineSeparator()),
            of(Schema.of(field("nullValue", () -> null)), "nullValue: null" + System.lineSeparator()),
            of(Schema.of(field("array", () -> new String[]{null, "test", "123"})),
                "array:" + System.lineSeparator()
                    + "  - null" + System.lineSeparator()
                    + "  - test" + System.lineSeparator()
                    + "  - 123" + System.lineSeparator()),
            of(Schema.of(field("array", () -> new Integer[]{123, 456, 789})),
                "array:" + System.lineSeparator()
                    + "  - 123" + System.lineSeparator()
                    + "  - 456" + System.lineSeparator()
                    + "  - 789" + System.lineSeparator()),
            of(Schema.of(field("array", () -> new Object[]{"test", 456, true})),
                "array:" + System.lineSeparator()
                    + "  - test" + System.lineSeparator()
                    + "  - 456" + System.lineSeparator()
                    + "  - true" + System.lineSeparator()),
            of(Schema.of(field("emptyarray", () -> new Long[]{})), "emptyarray:" + System.lineSeparator()),
            of(Schema.of(field("emptyarray", Collections::emptyList)), "emptyarray:" + System.lineSeparator()),
            of(Schema.of(field("key", () -> "value"),
                    field("nested", () -> Schema.of(field("nestedkey", () -> "nestedvalue")))),
                "key: value" + System.lineSeparator() + "nested:" + System.lineSeparator() + "  nestedkey: nestedvalue" + System.lineSeparator()),
            of(Schema.of(field("key", () -> "value"),
                    field("nested",
                        () -> Schema.of(field("nestedkey", () -> "nestedvalue"),
                            field("nested2", () -> Schema.of(field("nestedkey2", () -> "nestedvalue2")))))),
                "key: value" + System.lineSeparator()
                    + "nested:" + System.lineSeparator() + "  nestedkey: nestedvalue" + System.lineSeparator()
                    + "  nested2:" + System.lineSeparator()
                    + "    nestedkey2: nestedvalue2" + System.lineSeparator())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 8})
    void generateFromFakeSequence(int limit) {
        final BaseFaker faker = new BaseFaker();
        Schema<Name, String> schema = Schema.of(field("firstName", Name::firstName));

        YamlTransformer<Name> transformer = new YamlTransformer<>();
        String yaml =
            transformer.generate(
                faker.<Name>collection().suppliers(faker::name).maxLen(limit).build(),
                schema);

        int numberOfLines = 0;
        for (int i = 0; i < yaml.length(); i++) {
            if (yaml.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            }
        }

        assertThat(numberOfLines).isEqualTo((limit * (schema.getFields().length + 1)) - 1);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 8})
    void generateFromFakeSequenceWithCollection(int limit) {
        final BaseFaker faker = new BaseFaker();
        Schema<Name, List<String>> schema = Schema.of(field("firstNames", name -> IntStream.rangeClosed(1, limit)
            .mapToObj(it -> name.firstName()).collect(Collectors.toList())));

        YamlTransformer<Name> transformer = new YamlTransformer<>();
        String yaml =
            transformer.generate(
                faker.<Name>collection().suppliers(faker::name).maxLen(1).build(),
                schema);

        int numberOfLines = 0;
        for (int i = 0; i < yaml.length(); i++) {
            if (yaml.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            }
        }

        assertThat(numberOfLines).isEqualTo(limit + 1);
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
