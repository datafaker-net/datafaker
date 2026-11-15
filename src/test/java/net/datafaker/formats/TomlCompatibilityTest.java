package net.datafaker.formats;

import net.datafaker.transformations.Schema;
import net.datafaker.transformations.TomlTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class TomlCompatibilityTest {
    private final TomlTransformer<Object> transformer = new TomlTransformer<>();

    private TomlParseResult parse(String text) {
        TomlParseResult parsed = Toml.parse(text);
        assertThat(parsed.errors()).as(text).isEmpty();
        return parsed;
    }

    @Test
    void pathsRemainStringsAtEveryContainerDepth() {
        Path path = Path.of("a/b");
        Schema<Object, ?> schema = Schema.of(field("file", () -> path),
            field("list", () -> List.of(path)), field("array", () -> new Path[]{path}),
            field("stream", () -> Stream.of(path)), field("map", () -> Map.of("file", path)),
            field("child", () -> Schema.of(field("file", () -> path))));
        TomlParseResult parsed = parse(transformer.generate(schema, 1));
        assertThat(parsed.getString("file")).isEqualTo(path.toString());
        for (String key : List.of("list", "array", "stream")) {
            assertThat(parsed.getArray(key).toList()).containsExactly(path.toString());
        }
        assertThat(parsed.getString("map.file")).isEqualTo(path.toString());
        assertThat(parsed.getString("child.file")).isEqualTo(path.toString());
    }

    @Test
    void arbitraryIterablesUseToStringWithoutOpeningAnIterator() {
        Iterable<Object> iterable = new Iterable<>() {
            @Override
            public Iterator<Object> iterator() {
                throw new AssertionError("A non-Collection Iterable must not be traversed");
            }

            @Override
            public String toString() {
                return "text\n\"value\"";
            }
        };
        Iterable<Object> emptyText = new Iterable<>() {
            @Override
            public Iterator<Object> iterator() {
                return Collections.emptyIterator();
            }

            @Override
            public String toString() {
                return "abc";
            }
        };
        TomlParseResult parsed = parse(transformer.generate(Schema.of(
            field("value", () -> iterable), field("text", () -> emptyText)), 1));
        assertThat(parsed.getString("value")).isEqualTo(iterable.toString());
        assertThat(parsed.getString("text")).isEqualTo("abc");
    }

    private enum Key { FIRST }

    @Test
    void convertsNonStringMapKeysAndEscapesTheirText() {
        UUID uuid = UUID.fromString("f9d5574c-9a6a-4f4c-bcb7-8d80dd91918d");
        Object special = new Object() {
            @Override
            public String toString() {
                return "a.b\n\"key\"";
            }
        };
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put(1, "integer");
        map.put(Key.FIRST, "enum");
        map.put(uuid, "uuid");
        map.put(special, List.of(2, 3));
        TomlParseResult parsed = parse(transformer.generate(Schema.of(
            field("counts", () -> map), field("maps", () -> List.of(map))), 1));
        var table = parsed.getTable("counts");
        assertThat(table.size()).isEqualTo(4);
        assertThat(table.getString("1")).isEqualTo("integer");
        assertThat(table.getString("FIRST")).isEqualTo("enum");
        assertThat(table.getString(uuid.toString())).isEqualTo("uuid");
        assertThat(table.getArray(List.of(special.toString())).toList()).containsExactly(2L, 3L);
        assertThat(parsed.getArray("maps").getTable(0).getString("1")).isEqualTo("integer");
    }

    @Test
    void rejectsCollisionsAfterMapKeyConversionInsteadOfWritingDuplicateKeys() {
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put(1, "one");
        map.put("1", "another");
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("map", () -> map)), 1)).withMessageContaining("Duplicate").withMessageContaining("map.1");
        map.put(1, null);
        assertThat(parse(transformer.generate(Schema.of(field("map", () -> map)), 1))
            .getString("map.1")).isEqualTo("another");
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void rejectsInfiniteStreamsIncludingNullOnlyStreamsAndClosesThem(boolean nulls) {
        AtomicInteger closed = new AtomicInteger();
        AtomicInteger consumed = new AtomicInteger();
        Stream<Object> stream = Stream.<Object>generate(() -> {
            consumed.incrementAndGet();
            return nulls ? null : 1;
        }).onClose(closed::incrementAndGet);
        assertThatIllegalArgumentException().isThrownBy(() -> transformer.generate(
            Schema.of(field("ids", () -> stream)), 1))
            .withMessageContaining("100000").withMessageContaining("ids");
        assertThat(closed).hasValue(1);
        assertThat(consumed.get()).isBetween(100_000, 100_001);
    }

    @Test
    void acceptsFiniteStreamsAtTheLimitWithoutTruncatingCollectionsOrArrays() {
        TomlParseResult parsed = parse(transformer.generate(Schema.of(
            field("empty", () -> Stream.empty()),
            field("limit", () -> Stream.generate(() -> 1).limit(100_000)),
            field("list", () -> Collections.nCopies(100_001, 2)),
            field("array", () -> new int[100_001])), 1));
        assertThat(parsed.getArray("empty").size()).isZero();
        assertThat(parsed.getArray("limit").size()).isEqualTo(100_000);
        assertThat(parsed.getArray("list").size()).isEqualTo(100_001);
        assertThat(parsed.getArray("list").getLong(100_000)).isEqualTo(2L);
        assertThat(parsed.getArray("array").size()).isEqualTo(100_001);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void streamingRetainsRowAndLifecycleHooks(int limit) {
        List<String> calls = new ArrayList<>();
        TomlTransformer<Object> custom = new TomlTransformer<>() {
            @Override
            public String apply(Object input, Schema<Object, ?> schema, long rowId) {
                calls.add("row" + rowId);
                return "# row " + rowId + "\n" + super.apply(input, schema, rowId);
            }

            @Override
            public String getStartStream(Schema<Object, ?> schema) {
                calls.add("start");
                return "# start";
            }

            @Override
            public String getEndStream() {
                calls.add("end");
                return "# end";
            }

            @Override
            public String getLineSeparator() {
                return "\n# separator\n";
            }

            @Override
            public String getElementSeparator() {
                calls.add("element");
                return "\n# between";
            }
        };
        Schema<Object, ?> schema = Schema.of(field("x", () -> 5));
        String text = custom.generateStream(schema, limit).collect(Collectors.joining("\n"));
        TomlParseResult parsed = parse(text);
        if (limit == 0) {
            assertThat(calls).isEmpty();
            assertThat(text).isEmpty();
        } else {
            assertThat(text).contains("# start", "# end", "# separator", "# row 0");
            if (limit == 1) {
                assertThat(calls).containsExactly("start", "row0", "end");
                assertThat(parsed.getLong("x")).isEqualTo(5L);
            } else {
                assertThat(calls).containsExactly("start", "row0", "element", "element", "row1", "end");
                assertThat(parsed.getArray("records").size()).isEqualTo(2);
                assertThat(parsed.getArray("records").getTable(1).getLong("x")).isEqualTo(5L);
            }
        }
        calls.clear();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        custom.writeToOutputStream(output, schema, limit);
        String written = output.toString(StandardCharsets.UTF_8);
        assertThat(written.replace("\r\n", "\n").stripTrailing())
            .isEqualTo(text.replace("\r\n", "\n").stripTrailing());
    }
}
