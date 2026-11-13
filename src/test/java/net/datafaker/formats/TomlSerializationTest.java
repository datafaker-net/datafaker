package net.datafaker.formats;

import net.datafaker.transformations.Schema;
import net.datafaker.transformations.TomlTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.tomlj.Toml;
import org.tomlj.TomlArray;
import org.tomlj.TomlTable;
import org.tomlj.TomlParseResult;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.params.provider.Arguments.of;

class TomlSerializationTest {
    private final TomlTransformer<Object> transformer = new TomlTransformer<>();

    private Map<String, Object> parse(Schema<Object, ?> schema) {
        TomlParseResult result = Toml.parse(transformer.generate(schema, 1));
        assertThat(result.errors()).isEmpty();
        return tableToMap(result);
    }

    private Map<String, Object> tableToMap(TomlTable table) {
        Map<String, Object> result = new LinkedHashMap<>();
        table.toMap().forEach((key, value) -> result.put(key, parsedValue(value)));
        return result;
    }

    private Object parsedValue(Object value) {
        if (value instanceof TomlTable table) {
            return tableToMap(table);
        }
        if (value instanceof TomlArray array) {
            return IntStream.range(0, array.size()).mapToObj(i -> parsedValue(array.get(i))).toList();
        }
        return value;
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void keepsAssignmentsInTheirOwnTable(int position) {
        var fields = new ArrayList<>(List.of(
            field("child", () -> Schema.of(field("x", () -> 1))),
            field("items", () -> List.of(
                Schema.of(field("id", () -> 2)), Schema.of(field("id", () -> 3))))));
        fields.add(position, field("value", () -> 4));
        Schema<Object, ?> level = Schema.of(fields.toArray(net.datafaker.transformations.Field[]::new));
        Map<String, Object> expected = Map.of("value", 4L, "child", Map.of("x", 1L),
            "items", List.of(Map.of("id", 2L), Map.of("id", 3L)));

        assertThat(parse(level)).isEqualTo(expected);
        assertThat(parse(Schema.of(field("parent", () -> level))))
            .isEqualTo(Map.of("parent", expected));
        assertThat(parse(Schema.of(field("parents", () -> List.of(level, level)))))
            .isEqualTo(Map.of("parents", List.of(expected, expected)));
    }

    @Test
    void evaluatesEachUniqueFieldOnceInDeclarationOrder() {
        List<String> calls = new ArrayList<>();
        AtomicInteger ids = new AtomicInteger();
        Schema<Object, ?> item = Schema.of(
            field("child", () -> { calls.add("child"); return Schema.of(field("x", () -> 7)); }),
            field("id", () -> { calls.add("id"); return ids.incrementAndGet(); }));
        Schema<Object, ?> schema = Schema.of(
            field("items", () -> { calls.add("items"); return List.of(item, item); }),
            field("value", () -> { calls.add("value"); return 42; }),
            field("value", () -> { throw new AssertionError("Duplicate supplier must not run"); }),
            field("numbers", () -> { calls.add("numbers"); return new int[]{3, 1, 2}; }));

        assertThat(parse(schema)).isEqualTo(Map.of("value", 42L, "numbers", List.of(3L, 1L, 2L),
            "items", List.of(Map.of("id", 1L, "child", Map.of("x", 7L)),
                Map.of("id", 2L, "child", Map.of("x", 7L)))));
        assertThat(calls).containsExactly("items", "value", "numbers", "child", "id", "child", "id");
        assertThat(ids).hasValue(2);
    }

    @Test
    void preservesRelativeOrderWithinAssignmentsAndTables() {
        Schema<Object, ?> schema = Schema.of(
            field("firstTable", () -> Schema.of(field("id", () -> 1))),
            field("first", () -> 2),
            field("items", () -> List.of(Schema.of(field("id", () -> 3)))),
            field("empty", () -> List.of()),
            field("lastTable", () -> Schema.of(field("id", () -> 4))),
            field("last", () -> new int[]{5, 6}));
        assertThat(parse(schema)).isEqualTo(Map.of("first", 2L, "empty", List.of(), "last", List.of(5L, 6L),
            "firstTable", Map.of("id", 1L), "items", List.of(Map.of("id", 3L)), "lastTable", Map.of("id", 4L)));
        assertThat(transformer.generate(schema, 1).lines().toList()).containsExactly(
            "first = 2", "empty = []", "last = [ 5, 6 ]", "[firstTable]", "id = 1",
            "[[items]]", "id = 3", "[lastTable]", "id = 4");
    }

    @Test
    void omitsNullFieldsAndTheirHeadersAtEveryDepth() {
        AtomicInteger calls = new AtomicInteger();
        Schema<Object, ?> nullable = Schema.of(
            field("scalar", () -> { calls.incrementAndGet(); return (String) null; }),
            field("table", () -> { calls.incrementAndGet(); return (Schema<Object, ?>) null; }),
            field("tables", () -> { calls.incrementAndGet(); return (List<Schema<Object, ?>>) null; }),
            field("tableArray", () -> { calls.incrementAndGet(); return (Schema<?, ?>[]) null; }));
        assertThat(transformer.generate(nullable, 1)).isEmpty();
        assertThat(calls).hasValue(4);
        calls.set(0);
        Schema<Object, ?> schema = Schema.of(
            field("parent", () -> nullable),
            field("parents", () -> List.of(nullable, nullable)),
            field("nested", () -> List.of(List.of(nullable))),
            field("keep", () -> "null"));
        assertThat(parse(schema)).isEqualTo(Map.of("parent", Map.of(),
            "parents", List.of(Map.of(), Map.of()), "nested", List.of(List.of(Map.of())), "keep", "null"));
        assertThat(calls).hasValue(16);
        String text = transformer.generate(schema, 1);
        assertThat(text).doesNotContain("scalar", "table");
    }

    @Test
    void omitsNullFieldsWithoutChangingSiblingOwnershipOrSupplierCounts() {
        List<String> calls = new ArrayList<>();
        Schema<Object, ?> schema = Schema.of(
            field("before", () -> { calls.add("before"); return (String) null; }),
            field("child", () -> Schema.of(field("id", () -> 1), field("optional", () -> null))),
            field("middle", () -> { calls.add("middle"); return (Schema<Object, ?>) null; }),
            field("items", () -> List.of(Schema.of(field("optional", () -> null), field("id", () -> 2)))),
            field("after", () -> { calls.add("after"); return (List<?>) null; }),
            field("value", () -> { calls.add("value"); return 3; }),
            field("before", () -> { calls.add("fallback"); return 4; }));
        assertThat(parse(schema)).isEqualTo(Map.of("child", Map.of("id", 1L),
            "items", List.of(Map.of("id", 2L)), "value", 3L, "before", 4L));
        assertThat(calls).containsExactly("before", "middle", "after", "value", "fallback");
    }

    @Test
    void omitsNullableFieldsWhenTransformingInput() {
        Schema<String, String> schema = Schema.of(field("optional", input -> input.isEmpty() ? null : input),
            field("keep", input -> "present"));
        TomlTransformer<String> inputTransformer = new TomlTransformer<>();
        assertThat(inputTransformer.apply("", schema)).isEqualTo("keep = \"present\"");
        assertThat(inputTransformer.apply("value", schema).lines().toList())
            .containsExactly("optional = \"value\"", "keep = \"present\"");
    }

    @Test
    void rejectsNullKeysEvenWhenTheirValueWouldBeNull() {
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field(null, () -> null)), 1)).withMessageContaining("key");
    }

    @Test
    void preservesEscapedKeysWhenOmittingNullFields() {
        Schema<Object, ?> item = Schema.of(field("optional.field", () -> null), field("a.b", () -> 1));
        assertThat(parse(Schema.of(field("parent.name", () -> item),
            field("items list", () -> List.of(item)), field("root value", () -> 2),
            field("absent.table", () -> (Schema<Object, ?>) null))))
            .isEqualTo(Map.of("parent.name", Map.of("a.b", 1L),
                "items list", List.of(Map.of("a.b", 1L)), "root value", 2L));
    }

    @ParameterizedTest
    @MethodSource("nullValues")
    void omitsNullElementsAtEveryDepth(Object value, Object expected) {
        Schema<Object, ?> schema = Schema.of(field("value", () -> value));
        Map<String, Object> expectedTable = Map.of("value", expected);
        assertThat(parse(schema)).isEqualTo(expectedTable);
        assertThat(parse(Schema.of(field("parent", () -> schema))))
            .isEqualTo(Map.of("parent", expectedTable));
        assertThat(parse(Schema.of(field("parents", () -> List.of(Schema.of(), schema)))))
            .isEqualTo(Map.of("parents", List.of(Map.of(), expectedTable)));
    }

    static Stream<Arguments> nullValues() {
        return Stream.of(
            of(Arrays.asList(null, "a"), List.of("a")),
            of(Arrays.asList("a", null, "b"), List.of("a", "b")),
            of(Arrays.asList("a", null), List.of("a")),
            of(Arrays.asList(null, null), List.of()),
            of(new String[]{null, "a"}, List.of("a")),
            of(new String[]{"a", null, "b"}, List.of("a", "b")),
            of(new String[]{"a", null}, List.of("a")),
            of(new Object[]{null, null}, List.of()),
            of(List.of(Arrays.asList(1, null)), List.of(List.of(1L))),
            of(new Object[]{List.of((Object) new Object[]{1, null})}, List.of(List.of(List.of(1L)))),
            of(List.of((Object) new Object[]{List.of(Arrays.asList("a", null))}), List.of(List.of(List.of(List.of("a"))))),
            of(Arrays.asList(Schema.of(), null), List.of(Map.of())),
            of(new Object[]{Schema.of(), null}, List.of(Map.of())));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void rejectsNullKeysBeforeEvaluatingTheirSupplier(boolean literalFirst) {
        AtomicInteger calls = new AtomicInteger();
        var bad = field(null, () -> calls.incrementAndGet());
        var literal = field("null", () -> 2);
        Schema<Object, ?> schema = literalFirst ? Schema.of(literal, bad) : Schema.of(bad, literal);
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(schema, 1))
            .withMessageContaining("null").withMessageContaining("key");
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("parent", () -> schema)), 1)).withMessageContaining("parent");
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("parents", () -> List.of(schema))), 1)).withMessageContaining("parents[0]");
        assertThat(calls).hasValue(0);
    }

    @Test
    void preservesLiteralNullEmptyStringsAndEmptyContainers() {
        Schema<Object, ?> schema = Schema.of(
            field("null", () -> "null"), field("", () -> ""),
            field("list", () -> List.of()), field("array", () -> new int[0]),
            field("values", () -> Arrays.asList("null", "", List.of(), new String[0])));
        assertThat(parse(schema)).isEqualTo(Map.of("null", "null", "", "", "list", List.of(),
            "array", List.of(), "values", List.of("null", "", List.of(), List.of())));
        assertThat(parse(Schema.of(field("", () -> schema)))).isEqualTo(Map.of("", parse(schema)));
        assertThat(parse(Schema.of(field("", () -> List.of(schema)))))
            .isEqualTo(Map.of("", List.of(parse(schema))));
    }

    @ParameterizedTest
    @MethodSource("mixedSchemaValues")
    void rejectsSchemaMixedWithOrdinaryValues(Object value, String path) {
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("values", () -> value)), 1))
            .withMessageContaining("values" + path).withMessageContaining("Schema");
    }

    static Stream<Arguments> mixedSchemaValues() {
        Schema<Object, ?> schema = Schema.of(field("id", () -> 1));
        return Stream.of(
            of(List.of(schema, "x"), "[1]"), of(List.of("x", schema), "[1]"),
            of(List.of(1, schema, true), "[1]"),
            of(new Object[]{schema, "x"}, "[1]"), of(new Object[]{"x", schema}, "[1]"),
            of(new Object[]{1, schema, true}, "[1]"),
            of(List.of(List.of(schema, "x")), "[0][1]"),
            of(new Object[]{List.of("x", schema)}, "[0][1]"),
            of(List.of(schema, List.of()), "[1]"));
    }

    @Test
    void serializesSchemaArraysLikeSchemaCollections() {
        Schema<Object, ?> first = Schema.of(field("child", () -> Schema.of(field("x", () -> 5))),
            field("id", () -> 1));
        Schema<Object, ?> second = Schema.of(field("id", () -> 2));
        Map<String, Object> expected = Map.of("items", List.of(
            Map.of("id", 1L, "child", Map.of("x", 5L)), Map.of("id", 2L)), "value", 3L);
        for (Object container : List.of(new Schema[]{first, second}, List.of(first, second))) {
            assertThat(parse(Schema.of(field("items", () -> container), field("value", () -> 3))))
                .isEqualTo(expected);
        }
    }

    @Test
    void preservesRecursiveContainersAndScalarTypes() {
        Object values = List.of(
            List.of(3, 1, 2),
            new Object[]{new int[]{4, 5}, List.of(), new String[]{"a\n\"b", "null", ""}},
            new LinkedHashSet<>(List.of(true, "text", 2.5)),
            new boolean[]{true, false}, new char[]{'a', 'b'});
        assertThat(parse(Schema.of(field("values", () -> values))))
            .isEqualTo(Map.of("values", List.of(List.of(3L, 1L, 2L),
                List.of(List.of(4L, 5L), List.of(), List.of("a\n\"b", "null", "")),
                List.of(true, "text", 2.5), List.of(true, false), List.of("a", "b"))));
    }

    @Test
    void preservesSchemaValuesInsideNestedArrays() {
        Schema<Object, ?> item = Schema.of(field("child", () -> Schema.of(field("x", () -> 1))),
            field("id", () -> 2));
        assertThat(parse(Schema.of(field("values", () -> List.of(List.of(item, item))))))
            .isEqualTo(Map.of("values", List.of(List.of(
                Map.of("id", 2L, "child", Map.of("x", 1L)),
                Map.of("id", 2L, "child", Map.of("x", 1L))))));
    }

    @ParameterizedTest
    @MethodSource("specialFloats")
    void serializesNonFiniteFloats(Number value, String token, double expected) {
        String text = transformer.generate(Schema.of(field("value", () -> value)), 1);
        assertThat(text).isEqualTo("value = " + token);
        Map<String, Object> parsed = parse(Schema.of(
            field("parent", () -> Schema.of(field("value", () -> value))),
            field("list", () -> List.of(value)), field("array", () -> new Object[]{value}),
            field("primitive", () -> value instanceof Float ? new float[]{value.floatValue()} : new double[]{value.doubleValue()}),
            field("nested", () -> List.of(List.of(value)))));
        assertThat(parsed).isEqualTo(Map.of("parent", Map.of("value", expected),
            "list", List.of(expected), "array", List.of(expected), "primitive", List.of(expected),
            "nested", List.of(List.of(expected))));
        double actual = (Double) ((Map<?, ?>) parsed.get("parent")).get("value");
        if (Double.isNaN(expected)) {
            assertThat(Double.isNaN(actual)).isTrue();
        } else {
            assertThat(actual).isEqualTo(expected);
        }
    }

    static Stream<Arguments> specialFloats() {
        return Stream.of(of(Double.NaN, "nan", Double.NaN), of(Float.NaN, "nan", Double.NaN),
            of(Double.POSITIVE_INFINITY, "inf", Double.POSITIVE_INFINITY),
            of(Float.POSITIVE_INFINITY, "inf", Double.POSITIVE_INFINITY),
            of(Double.NEGATIVE_INFINITY, "-inf", Double.NEGATIVE_INFINITY),
            of(Float.NEGATIVE_INFINITY, "-inf", Double.NEGATIVE_INFINITY));
    }

    @Test
    void preservesFiniteNumbersAndPrecision() {
        assertThat(parse(Schema.of(field("values", () -> List.of(1, 2.5f, 3.25d, -0.0f, -0.0d,
            new BigInteger("9223372036854775807"), new BigDecimal("123.125"))))))
            .isEqualTo(Map.of("values", List.of(1L, 2.5d, 3.25d, -0.0d, -0.0d, Long.MAX_VALUE, 123.125d)));
        assertThat(transformer.generate(Schema.of(field("value", () ->
            new BigDecimal("12345678901234567890.12345678901234567890"))), 1))
            .isEqualTo("value = 12345678901234567890.12345678901234567890");
    }
}
