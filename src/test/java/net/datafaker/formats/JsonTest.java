package net.datafaker.formats;

import static net.datafaker.transformations.Field.compositeField;
import static net.datafaker.transformations.Field.field;
import static net.datafaker.transformations.Transformer.LINE_SEPARATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.of;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Name;
import net.datafaker.sequence.FakeSequence;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JsonTransformer;
import net.datafaker.transformations.JsonTransformer.JsonTransformerBuilder.FormattedAs;
import net.datafaker.transformations.Schema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JsonTest {
    @Test
    void testGenerateFromSchemaWithLimit() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Object, ?> schema = Schema.of(
            field("Text", () -> faker.name().firstName()),
            field("Bool", () -> faker.bool().bool())
        );

        JsonTransformer<Object> transformer = JsonTransformer.builder().build();
        String json = transformer.generate(schema, 2);
        String expected = "{" + LINE_SEPARATOR +
            "{\"Text\": \"Willis\", \"Bool\": false}," + LINE_SEPARATOR +
            "{\"Text\": \"Carlena\", \"Bool\": true}" + LINE_SEPARATOR +
            "}";

        assertThat(json).isEqualTo(expected);
    }

    @Test
    void testGenerateFromFakeSequenceCollectionWithoutComma() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Integer, ?> schema = Schema.of(
            field("Number", integer -> integer),
            field("Password", integer -> faker.internet().password(integer, integer))
        );

        JsonTransformer<Integer> transformer = JsonTransformer.<Integer>builder().withCommaBetweenObjects(false).build();
        FakeSequence<Integer> fakeSequence = faker.<Integer>collection()
            .suppliers(() -> faker.number().randomDigit())
            .len(5)
            .build();

        String json = transformer.generate(fakeSequence, schema);

        String expected = "{" + LINE_SEPARATOR +
            "{\"Number\": 3, \"Password\": \"wo9\"}" + LINE_SEPARATOR +
            "{\"Number\": 6, \"Password\": \"691u00\"}" + LINE_SEPARATOR +
            "{\"Number\": 7, \"Password\": \"2249sil\"}" + LINE_SEPARATOR +
            "{\"Number\": 1, \"Password\": \"5\"}" + LINE_SEPARATOR +
            "{\"Number\": 3, \"Password\": \"mq6\"}" + LINE_SEPARATOR +
            "}";

        assertThat(json).isEqualTo(expected);
    }

    @Test
    void testGenerateFromFakeSequenceCollection() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Integer, ?> schema = Schema.of(
            field("Number", integer -> integer),
            field("Password", integer -> faker.internet().password(integer, integer))
        );

        JsonTransformer<Integer> transformer = JsonTransformer.<Integer>builder().build();
        FakeSequence<Integer> fakeSequence = faker.<Integer>collection()
            .suppliers(() -> faker.number().randomDigit())
            .len(5)
            .build();

        String json = transformer.generate(fakeSequence, schema);

        String expected = "{" + LINE_SEPARATOR +
            "{\"Number\": 3, \"Password\": \"wo9\"}," + LINE_SEPARATOR +
            "{\"Number\": 6, \"Password\": \"691u00\"}," + LINE_SEPARATOR +
            "{\"Number\": 7, \"Password\": \"2249sil\"}," + LINE_SEPARATOR +
            "{\"Number\": 1, \"Password\": \"5\"}," + LINE_SEPARATOR +
            "{\"Number\": 3, \"Password\": \"mq6\"}" + LINE_SEPARATOR +
            "}";

        assertThat(json).isEqualTo(expected);
    }

	@Test
    void testGenerateFromFakeSequenceStream() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Integer, ?> schema = Schema.of(
            field("Number", integer -> integer),
            field("Password", integer -> faker.internet().password(integer, integer))
        );

        JsonTransformer<Integer> transformer = JsonTransformer.<Integer>builder()
            .formattedAs(FormattedAs.JSON_ARRAY).withCommaBetweenObjects(false).build();
        FakeSequence<Integer> fakeSequence = faker.<Integer>stream()
            .suppliers(() -> faker.number().randomDigit())
            .len(2)
            .build();

        String json = transformer.generate(fakeSequence, schema);

        String expected = "[" + LINE_SEPARATOR +
            "{\"Number\": 3, \"Password\": \"429\"}" + LINE_SEPARATOR +
            "{\"Number\": 1, \"Password\": \"4\"}" + LINE_SEPARATOR +
            "]";

        assertThat(json).isEqualTo(expected);
    }

	@Test
    void testGenerateFromInfiniteFakeSequence() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Integer, ?> schema = Schema.of(
            field("Number", integer -> integer),
            field("Password", integer -> faker.internet().password(integer, integer))
        );

        JsonTransformer<Integer> transformer = JsonTransformer.<Integer>builder()
            .formattedAs(FormattedAs.JSON_ARRAY).build();
        FakeSequence<Integer> fakeSequence = faker.<Integer>stream()
            .suppliers(() -> faker.number().randomDigit())
            .build();

        assertThatThrownBy(() -> transformer.generate(fakeSequence, schema))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The sequence should be finite of size");
    }

    @ParameterizedTest
    @MethodSource("generateTestSchema")
    void simpleJsonTestForJsonTransformer(Schema<String, String> schema, String expected) {
        JsonTransformer<String> transformer = JsonTransformer.<String>builder().build();
        assertThat(transformer.generate(schema, 1)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("generateTestSchema")
    void outputArrayJsonTestForJsonTransformer(
        Schema<String, String> schema, String expected) {
        JsonTransformer<String> transformer = JsonTransformer.<String>builder().formattedAs(FormattedAs.JSON_ARRAY).build();

        assertThat(transformer.generate(schema, 2).replaceAll(System.lineSeparator(), ""))
            .isEqualTo("[" + expected + "," + expected + "]");
    }

    @ParameterizedTest
    @MethodSource("generateTestSchema")
    void outputWithoutCommaForJsonTransformer(
        Schema<String, String> schema, String expected) {
        JsonTransformer<String> transformer = JsonTransformer.<String>builder().withCommaBetweenObjects(false).build();

        assertThat(transformer.generate(schema, 2).replaceAll(System.lineSeparator(), ""))
            .isEqualTo("{" + expected +  expected + "}");
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

    @Test
    void jsonWithDifferentFieldFormatsInOneObjectTest() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        final int limit = 2;
        JsonTransformer<Object> transformer = JsonTransformer.builder().build();

        String json = transformer.generate(
            faker.collection().minLen(limit).maxLen(limit)
                .suppliers(faker::name)
                .build(), Schema.<Object, Object>of(
                field("text", () -> faker.name().firstName()),
                field("array", () ->
                    faker
                        .collection()
                        .suppliers(() -> faker.phoneNumber().phoneNumber())
                        .maxLen(3)
                        .generate()
                )
            ));

        int numberOfLines = 0;
        for (int i = 0; i < json.length(); i++) {
            if (json.regionMatches(i, "},", 0, "},".length())) {
                numberOfLines++;
            }
        }
        assertThat(numberOfLines).isEqualTo(limit - 1);
    }

    @Test
    void jsonObjectCollectionTest() {
        JsonTransformer<Name> transformer = JsonTransformer.<Name>builder().build();

        String json = transformer.generate(
            Schema.of(
                field("text", () -> "Mrs. Brian Braun"),
                field("objectCollection", () -> List.of(
                    compositeField(null, new Field[]{
                            field("country", () -> "Denmark"),
                            field("city", () -> "Port Angel")
                        }
                    ),
                    compositeField(null, new Field[]{
                            field("two", () -> "Denmark"),
                            field("one", () -> "Port Angel")
                        }
                    )
                    )
                )
            ), 1);
        assertThat(json).isEqualTo("{\"text\": \"Mrs. Brian Braun\", " +
            "\"objectCollection\": [{\"country\": \"Denmark\", \"city\": \"Port Angel\"}, {\"two\": \"Denmark\", \"one\": \"Port Angel\"}]}");
    }

    @Test
    void jsonCollectionOfCollectionsTest() {
        JsonTransformer<Name> transformer = JsonTransformer.<Name>builder().build();

        String json = transformer.generate(
            Schema.of(
                field("text", () -> "Mrs. Brian Braun"),
                field("objectCollection", () -> List.of(
                        List.of(
                            List.of(
                                compositeField(null, new Field[]{
                                        field("country", () -> "Denmark"),
                                        field("city", () -> "Port Angel")
                                    }
                                ),
                                compositeField(null, new Field[]{
                                        field("two", () -> "Denmark"),
                                        field("one", () -> "Port Angel")
                                    }
                                )
                            )
                        )
                    )
                )
            ), 1);
        assertThat(json).isEqualTo("{\"text\": \"Mrs. Brian Braun\", " +
            "\"objectCollection\": [[[{\"country\": \"Denmark\", \"city\": \"Port Angel\"}, {\"two\": \"Denmark\", \"one\": \"Port Angel\"}]]]}");
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
