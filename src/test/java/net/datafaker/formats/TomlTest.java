package net.datafaker.formats;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Name;
import net.datafaker.transformations.Schema;
import net.datafaker.transformations.TomlTransformer;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class TomlTest {

    @ParameterizedTest
    @MethodSource("generateTestSchema")
    void simpleTomlTest(Schema<String, String> schema, String expected) {
        TomlTransformer<String> tomlTransformer = new TomlTransformer<>();
        assertThat(tomlTransformer.generate(schema, 1)).isEqualTo(expected);
    }

    @ParameterizedTest(name = "Escape control character U+{0}")
    @MethodSource("controlCharacters")
    void escapesControlCharacters(String codePoint, String input, String expectedEscape) {
        Schema<String, String> schema = Schema.of(field("message", () -> "before" + input + "after"));
        TomlTransformer<String> transformer = new TomlTransformer<>();

        assertThat(transformer.generate(schema, 1))
            .isEqualTo("message = \"before" + expectedEscape + "after\"");
    }

    private static Stream<Arguments> controlCharacters() {
        return IntStream.concat(IntStream.rangeClosed(0, 0x1F), IntStream.of(0x7F))
            .mapToObj(codePoint -> {
                String expectedEscape = switch (codePoint) {
                    case '\b' -> "\\b";
                    case '\t' -> "\\t";
                    case '\n' -> "\\n";
                    case '\f' -> "\\f";
                    case '\r' -> "\\r";
                    default -> String.format("\\u%04X", codePoint);
                };
                return of(String.format("%04X", codePoint),
                    String.valueOf((char) codePoint), expectedEscape);
            });
    }

    private static Stream<Arguments> generateTestSchema() {
        String sep = System.lineSeparator();
        return Stream.of(
            of(Schema.of(), ""),
            of(Schema.of(field("key", () -> "value")), "key = \"value\""),
            // A literal newline in the input must be escaped as \n in the generated TOML string.
            of(Schema.of(field("message", () -> "line1\nline2")), "message = \"line1\\nline2\""),
            // A carriage return(\r) must also be escaped in the generated TOML string.
            of(Schema.of(field("message", () -> "line1\rline2")), "message = \"line1\\rline2\""),
            // Preserve both characters of a Windows-style line ending(\r\n) as TOML escapes.
            of(Schema.of(field("message", () -> "line1\r\nline2")), "message = \"line1\\r\\nline2\""),
            // Backspace and form feed must be escaped in TOML basic strings.
            of(Schema.of(field("message", () -> "a\bb")), "message = \"a\\bb\""),
            of(Schema.of(field("message", () -> "a\fb")), "message = \"a\\fb\""),
            // Serialize a tab using its escape sequence.
            of(Schema.of(field("message", () -> "a\tb")), "message = \"a\\tb\""),
            // Keep escaping double quotes and backslashes correctly.
            of(Schema.of(field("message", () -> "say \"hello\"")), "message = \"say \\\"hello\\\"\""),
            of(Schema.of(field("message", () -> "C:\\temp")), "message = \"C:\\\\temp\""),
            // A literal backslash followed by n must not become an actual newline.
            of(Schema.of(field("message", () -> "line1\\nline2")), "message = \"line1\\\\nline2\""),
            // Preserve Unicode text while escaping mixed special characters.
            of(Schema.of(field("message", () -> "中文😀\n\"quoted\"\\path")),
                "message = \"中文😀\\n\\\"quoted\\\"\\\\path\""),
            of(Schema.of(field("number", () -> 123)), "number = 123"),
            of(Schema.of(field("number", () -> BigDecimal.valueOf(123.0))), "number = 123.0"),
            of(Schema.of(field("number", () -> BigDecimal.valueOf(123.123))), "number = 123.123"),
            of(Schema.of(field("boolean", () -> true)), "boolean = true"),
            of(Schema.of(field("nullValue", () -> null)), "nullValue = null"),
            of(Schema.of(field("array", () -> new @Nullable String[]{null, "test", "123"})),
                "array = [ null, \"test\", \"123\" ]"),
            of(Schema.of(field("array", () -> new Integer[]{123, 456, 789})),
                "array = [ 123, 456, 789 ]"),
            of(Schema.of(field("array", () -> new Object[]{"test", 456, true})),
                "array = [ \"test\", 456, true ]"),
            of(Schema.of(field("emptyarray", () -> new Long[]{})), "emptyarray = []"),
            of(Schema.of(field("emptyarray", Collections::emptyList)), "emptyarray = []"),
            of(Schema.of(
                field("key", () -> "value"),
                field("nested", () -> Schema.of(field("nestedkey", () -> "nestedvalue")))
            ), "key = \"value\"" + sep +
                "[nested]" + sep +
                "nestedkey = \"nestedvalue\""),
            of(Schema.of(
                field("key", () -> "value"),
                field("nested", () -> Schema.of(
                    field("nestedkey", () -> "nestedvalue"),
                    field("nested2", () -> Schema.of(
                        field("nestedkey2", () -> "nestedvalue2")
                    ))
                ))
            ), "key = \"value\"" + sep +
                "[nested]" + sep +
                "nestedkey = \"nestedvalue\"" + sep +
                "[nested.nested2]" + sep +
                "nestedkey2 = \"nestedvalue2\""),
            of(Schema.of(
                field("parent", () -> Schema.of(
                    field("numbers", () -> new Integer[]{1, 2})
                ))
            ), "[parent]" + sep +
                "numbers = [ 1, 2 ]"),
            of(Schema.of(
                field("items", () -> List.of(
                    Schema.of(field("id", () -> 1)),
                    Schema.of(field("id", () -> 2))
                ))
            ), "[[items]]" + sep +
                "id = 1" + sep +
                "[[items]]" + sep +
                "id = 2")
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 8})
    void generateFromFakeSequence(int limit) {
        final BaseFaker faker = new BaseFaker();
        Schema<Name, String> schema = Schema.of(field("firstName", Name::firstName));

        TomlTransformer<Name> transformer = new TomlTransformer<>();

        String toml = transformer.generate(
            faker.<Name>collection().suppliers(faker::name).maxLen(limit).build(),
            schema);

        int numberOfLines = 0;
        for (int i = 0; i < toml.length(); i++) {
            if (toml.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            }
        }

        assertThat(numberOfLines).isEqualTo((limit * (schema.getFields().length)) - 1);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 8})
    void generateFromFakeSequenceWithCollection(int limit) {
        final BaseFaker faker = new BaseFaker();
        Schema<Name, List<String>> schema = Schema.of(field("firstNames", name -> IntStream.rangeClosed(1, limit)
            .mapToObj(it -> name.firstName())
            .toList()));

        TomlTransformer<Name> transformer = new TomlTransformer<>();

        String toml = transformer.generate(
            faker.<Name>collection().suppliers(faker::name).maxLen(1).build(),
            schema);

        assertThat(toml).matches("firstNames = \\[ \"(.+\",?){" + limit + "} ]");
    }

}
