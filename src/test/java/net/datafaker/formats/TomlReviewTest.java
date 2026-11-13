package net.datafaker.formats;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Name;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.Schema;
import net.datafaker.transformations.TomlTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.tomlj.Toml;
import org.tomlj.TomlArray;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static net.datafaker.transformations.Field.compositeField;
import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class TomlReviewTest {
    private final TomlTransformer<Object> transformer = new TomlTransformer<>();

    private TomlParseResult parse(String text) {
        TomlParseResult parsed = Toml.parse(text);
        assertThat(parsed.errors()).as(text).isEmpty();
        return parsed;
    }

    @Test
    void compositeFieldsBecomeTablesAndKeepTheirInput() {
        Name input = new BaseFaker().name();
        AtomicInteger calls = new AtomicInteger();
        Schema<Name, ?> schema = Schema.of(compositeField("address", new Field[]{
            field("city", (Name actual) -> {
                assertThat(actual).isSameAs(input);
                calls.incrementAndGet();
                return "Paris";
            }),
            compositeField("nested", new Field[]{field("zip", () -> 123)})
        }));
        TomlParseResult result = parse(new TomlTransformer<Name>().apply(input, schema));
        assertThat(result.getString("address.city")).isEqualTo("Paris");
        assertThat(result.getLong("address.nested.zip")).isEqualTo(123L);
        assertThat(calls).hasValue(1);
    }

    @Test
    void propagatesInputToTablesArrayTablesInlineSchemasAndMaps() {
        AtomicInteger calls = new AtomicInteger();
        Schema<String, ?> child = Schema.of(field("value", (String input) -> {
            calls.incrementAndGet();
            return input;
        }));
        Schema<String, ?> schema = Schema.of(
            field("child", () -> child),
            field("items", () -> new Schema[]{child, child}),
            field("nested", () -> List.of(List.of(child))),
            field("map", () -> Map.of("schema", child)));
        TomlParseResult result = parse(new TomlTransformer<String>().apply("input", schema));
        assertThat(result.getString("child.value")).isEqualTo("input");
        assertThat(result.getArray("items").size()).isEqualTo(2);
        for (int i = 0; i < 2; i++) {
            assertThat(result.getArray("items").getTable(i).getString("value")).isEqualTo("input");
        }
        assertThat(result.getArray("nested").getArray(0).getTable(0).getString("value")).isEqualTo("input");
        assertThat(result.getString("map.schema.value")).isEqualTo("input");
        assertThat(calls).hasValue(5);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void generatesOneValidDocumentWithAllRecords(int count) {
        AtomicInteger calls = new AtomicInteger();
        Schema<Object, ?> schema = Schema.of(
            field("id", calls::incrementAndGet),
            field("child", () -> Schema.of(field("x", () -> 2))),
            field("items", () -> List.of(Schema.of(field("x", () -> 3)))));
        String output = transformer.generate(schema, count);
        TomlParseResult result = parse(output);
        assertThat(calls).hasValue(count);
        if (count == 0) {
            assertThat(output).isEmpty();
        } else if (count == 1) {
            assertThat(result.getLong("id")).isEqualTo(1L);
            assertThat(result.getLong("child.x")).isEqualTo(2L);
            assertThat(result.contains("records")).isFalse();
        } else {
            TomlArray records = result.getArray("records");
            assertThat(records.size()).isEqualTo(count);
            for (int i = 0; i < count; i++) {
                TomlTable record = records.getTable(i);
                assertThat(record.getLong("id")).isEqualTo(i + 1L);
                assertThat(record.getLong("child.x")).isEqualTo(2L);
                assertThat(record.getArray("items").getTable(0).getLong("x")).isEqualTo(3L);
            }
        }
    }

    @Test
    void iterableAndStreamingGenerationUseTheSameRecordLayout() {
        Schema<String, ?> schema = Schema.of(field("name", (String input) -> input),
            field("child", () -> Schema.of(field("value", (String input) -> input))));
        TomlTransformer<String> inputTransformer = new TomlTransformer<>();
        TomlArray records = parse(inputTransformer.generate(List.of("a", "b"), schema)).getArray("records");
        assertThat(records.size()).isEqualTo(2);
        assertThat(records.getTable(0).getString("child.value")).isEqualTo("a");
        assertThat(records.getTable(1).getString("name")).isEqualTo("b");
        assertThat(parse(inputTransformer.generate(List.of("a"), schema)).getString("name")).isEqualTo("a");
        assertThat(inputTransformer.generate(List.of(), schema)).isEmpty();
        AtomicInteger calls = new AtomicInteger();
        Schema<Object, ?> generated = Schema.of(field("records", () -> calls.incrementAndGet()));
        String output = transformer.generateStream(generated, 2).collect(java.util.stream.Collectors.joining("\n"));
        TomlArray streamed = parse(output).getArray("records");
        assertThat(streamed.getTable(0).getLong("records")).isEqualTo(1L);
        assertThat(streamed.getTable(1).getLong("records")).isEqualTo(2L);
        assertThat(calls).hasValue(2);
        assertThat(transformer.generateStream(generated, 0).count()).isZero();
    }

    @Test
    void nullOnlyRecordsKeepTheirPositions() {
        Schema<Object, ?> schema = Schema.of(field("x", () -> null));
        TomlArray records = parse(transformer.generate(schema, 2)).getArray("records");
        assertThat(records.size()).isEqualTo(2);
        assertThat(records.getTable(0).isEmpty()).isTrue();
        assertThat(records.getTable(1).isEmpty()).isTrue();
    }

    @Test
    void usesFirstNonNullDuplicateAndNeverEvaluatesLaterDuplicates() {
        List<Integer> calls = new ArrayList<>();
        Schema<Object, ?> schema = Schema.of(
            field("x", () -> { calls.add(1); return null; }),
            field("x", () -> { calls.add(2); return null; }),
            field("x", () -> { calls.add(3); return 5; }),
            field("x", () -> { throw new AssertionError("Already resolved"); }));
        assertThat(parse(transformer.generate(schema, 1)).getLong("x")).isEqualTo(5L);
        assertThat(calls).containsExactly(1, 2, 3);
    }

    @Test
    void traversesContainersOnceAndClosesStreams() {
        AtomicInteger traversals = new AtomicInteger();
        var collection = new AbstractCollection<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                assertThat(traversals.incrementAndGet()).isEqualTo(1);
                return List.of(3, 1, 2).iterator();
            }

            @Override
            public int size() {
                return 3;
            }
        };
        AtomicInteger iterableCalls = new AtomicInteger();
        Iterable<String> iterable = () -> {
            iterableCalls.incrementAndGet();
            return Arrays.asList("a", null, "b").iterator();
        };
        AtomicInteger consumed = new AtomicInteger();
        AtomicInteger closed = new AtomicInteger();
        Stream<Integer> stream = Stream.of(1, null, 2).peek(value -> consumed.incrementAndGet())
            .onClose(closed::incrementAndGet);
        Schema<Object, ?> schema = Schema.of(field("list", () -> collection),
            field("iterable", () -> iterable), field("stream", () -> stream));
        TomlParseResult result = parse(transformer.generate(schema, 1));
        assertThat(result.getArray("list").toList()).containsExactly(3L, 1L, 2L);
        assertThat(result.getArray("iterable").toList()).containsExactly("a", "b");
        assertThat(result.getArray("stream").toList()).containsExactly(1L, 2L);
        assertThat(traversals).hasValue(1);
        assertThat(iterableCalls).hasValue(1);
        assertThat(consumed).hasValue(3);
        assertThat(closed).hasValue(1);
    }

    @Test
    void mapsBecomeInlineTablesWithEscapedKeysAndOmittedNullValues() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("a.b", 1);
        map.put("optional", null);
        map.put("", "null");
        map.put("nested", Map.of("values", new Object[]{"a\nb", null, 2}));
        TomlParseResult result = parse(transformer.generate(Schema.of(field("map", () -> map),
            field("maps", () -> List.of(map, Map.of()))), 1));
        TomlTable table = result.getTable("map");
        assertThat(table.getLong(List.of("a.b"))).isEqualTo(1L);
        assertThat(table.getString(List.of(""))).isEqualTo("null");
        assertThat(table.contains("optional")).isFalse();
        assertThat(table.getArray("nested.values").toList()).containsExactly("a\nb", 2L);
        assertThat(result.getArray("maps").size()).isEqualTo(2);
        assertThat(result.getArray("maps").getTable(1).isEmpty()).isTrue();
    }

    @Test
    void rejectsInvalidMapKeysInsteadOfLosingTheirIdentity() {
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put(null, null);
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("map", () -> map)), 1)).withMessageContaining("map").withMessageContaining("key");
        map.clear();
        map.put(1, "x");
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("map", () -> map)), 1)).withMessageContaining("map").withMessageContaining("key");
    }

    @ParameterizedTest
    @MethodSource("cycles")
    void rejectsCyclesWithPaths(Supplier<Object> cycle) {
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("value", cycle)), 1)).withMessageContaining("Cyclic").withMessageContaining("value");
    }

    static Stream<Supplier<Object>> cycles() {
        return Stream.of(
            () -> { List<Object> list = new ArrayList<>(); list.add(1); list.add(list); return list; },
            () -> { Object[] array = new Object[1]; array[0] = array; return array; },
            () -> { Map<String, Object> map = new LinkedHashMap<>(); map.put("self", map); return map; },
            () -> { List<Object> list = new ArrayList<>(); Object[] array = {list}; list.add(array); return list; },
            () -> { Schema<Object, ?>[] schema = new Schema[1];
                schema[0] = Schema.of(field("self", () -> schema[0])); return schema[0]; },
            () -> { List<Object> list = new ArrayList<>();
                list.add(Schema.of(field("self", () -> list))); return list; });
    }

    @Test
    void allowsSharedObjectsAndEqualIndependentContainers() {
        List<Integer> list = List.of(1, 2);
        Map<String, Object> map = Map.of("list", list);
        TomlParseResult result = parse(transformer.generate(Schema.of(
            field("values", () -> List.of(list, list, new ArrayList<>(list))),
            field("maps", () -> List.of(map, map))), 1));
        assertThat(result.getArray("values").size()).isEqualTo(3);
        assertThat(result.getArray("maps").size()).isEqualTo(2);
    }

    @Test
    void rejectsExcessiveDepthClearly() {
        Object nested = 1;
        for (int i = 0; i < 200; i++) {
            nested = List.of(nested);
        }
        Object value = nested;
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("deep", () -> value)), 1)).withMessageContaining("nesting").withMessageContaining("deep");
    }

    @ParameterizedTest
    @MethodSource("outOfRangeIntegers")
    void rejectsOutOfRangeIntegerTokens(Number value) {
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("value", () -> List.of(value))), 1))
            .withMessageContaining("64-bit").withMessageContaining("value[0]");
    }

    static Stream<Number> outOfRangeIntegers() {
        return Stream.of(new BigInteger("9223372036854775808"), new BigInteger("-9223372036854775809"),
            new BigDecimal("9223372036854775808"), new BigDecimal("-9223372036854775809"));
    }

    @Test
    void preservesIntegerLimitsAndLargeDecimalTokens() {
        TomlParseResult result = parse(transformer.generate(Schema.of(
            field("values", () -> List.of(new BigInteger("-9223372036854775808"),
                new BigInteger("9223372036854775807"))),
            field("decimal", () -> new BigDecimal("9223372036854775808.5")),
            field("exponent", () -> new BigDecimal("1E+30"))), 1));
        assertThat(result.getArray("values").toList()).containsExactly(Long.MIN_VALUE, Long.MAX_VALUE);
        assertThat(result.get("decimal")).isInstanceOf(Double.class);
        assertThat(result.getDouble("exponent")).isEqualTo(1E30);
    }

    @Test
    void filtersNullsBeforeClassifyingSchemaSequences() {
        Schema<Object, ?> item = Schema.of(field("id", () -> 7));
        Object[] array = {null, item, null, item};
        Iterable<Object> iterable = () -> Arrays.<Object>asList(null, item, null, item).iterator();
        TomlParseResult result = parse(transformer.generate(Schema.of(
            field("array", () -> array), field("iterable", () -> iterable),
            field("stream", () -> Stream.of(null, item, null, item))), 1));
        for (String key : List.of("array", "iterable", "stream")) {
            TomlArray values = result.getArray(key);
            assertThat(values.size()).isEqualTo(2);
            assertThat(values.getTable(0).getLong("id")).isEqualTo(7L);
            assertThat(values.getTable(1).getLong("id")).isEqualTo(7L);
        }
    }

    @Test
    void diagnosticsKeepOriginalIndicesAfterNullFiltering() {
        Object[] cycle = new Object[2];
        cycle[1] = cycle;
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("value", () -> cycle)), 1)).withMessageContaining("value[1]");
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("value", () -> Arrays.asList(null, 1, null, Schema.of()))), 1))
            .withMessageContaining("value[3]");
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("value", () -> new Object[]{null, new BigInteger("9223372036854775808")})), 1))
            .withMessageContaining("value[1]");
    }

    @Test
    void duplicateNullFieldsCanFallBackToNestedSchemas() {
        AtomicInteger calls = new AtomicInteger();
        Schema<Object, ?> fallback = Schema.of(
            field("x", () -> { calls.incrementAndGet(); return null; }),
            field("x", () -> { calls.incrementAndGet(); return Schema.of(field("id", () -> 1)); }),
            field("x", () -> { throw new AssertionError("Already resolved"); }));
        TomlParseResult result = parse(transformer.generate(Schema.of(
            field("table", () -> fallback), field("items", () -> List.of(fallback)),
            field("inline", () -> Map.of("nested", fallback))), 1));
        assertThat(result.getLong("table.x.id")).isEqualTo(1L);
        assertThat(result.getArray("items").getTable(0).getLong("x.id")).isEqualTo(1L);
        assertThat(result.getLong("inline.nested.x.id")).isEqualTo(1L);
        assertThat(calls).hasValue(6);
    }

    @Test
    void batchNullFilteringDoesNotAbortGeneration() {
        AtomicInteger calls = new AtomicInteger();
        Schema<Object, ?> schema = Schema.of(field("values", () ->
            new Object[]{calls.incrementAndGet(), null, "null"}));
        TomlArray records = parse(transformer.generate(schema, 1000)).getArray("records");
        assertThat(records.size()).isEqualTo(1000);
        for (int i = 0; i < records.size(); i++) {
            assertThat(records.getTable(i).getArray("values").toList()).containsExactly(i + 1L, "null");
        }
        assertThat(calls).hasValue(1000);
    }

    @Test
    void nullOnlyIterableAndStreamValuesStayEmptyArrays() {
        Iterable<Object> iterable = () -> Arrays.<Object>asList(null, null).iterator();
        TomlParseResult result = parse(transformer.generate(Schema.of(
            field("iterable", () -> iterable), field("stream", () -> Stream.of(null, null))), 1));
        assertThat(result.getArray("iterable").size()).isZero();
        assertThat(result.getArray("stream").size()).isZero();
    }

    @Test
    void closesAStreamWhenItsElementsAreInvalid() {
        AtomicInteger closed = new AtomicInteger();
        Stream<Object> stream = Stream.of("x", Schema.of()).onClose(closed::incrementAndGet);
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("stream", () -> stream)), 1)).withMessageContaining("stream[1]");
        assertThat(closed).hasValue(1);
    }
}
