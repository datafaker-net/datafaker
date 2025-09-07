package net.datafaker.formats;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Name;
import net.datafaker.sequence.FakeSequence;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JsonTransformer;
import net.datafaker.transformations.Schema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.datafaker.transformations.Field.compositeField;
import static net.datafaker.transformations.Field.field;
import static net.datafaker.transformations.Transformer.LINE_SEPARATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;

class JsonTest {
    @Test
    void testJsonStream() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Object, ?> schema = Schema.of(
            field("Text", () -> faker.name().firstName()),
            field("Bool", () -> faker.bool().bool())
        );

        JsonTransformer<Object> transformer = JsonTransformer.builder().build();
        Stream<CharSequence> json = transformer.generateStream(schema, 10);
        String output = json.collect(Collectors.joining(LINE_SEPARATOR));
        assertThat(output).isEqualTo("[" + LINE_SEPARATOR +
            "{\"Text\": \"Willis\", \"Bool\": false}," + LINE_SEPARATOR +
            "{\"Text\": \"Carlena\", \"Bool\": true}," + LINE_SEPARATOR +
            "{\"Text\": \"Stephnie\", \"Bool\": true}," + LINE_SEPARATOR +
            "{\"Text\": \"Rutha\", \"Bool\": true}," + LINE_SEPARATOR +
            "{\"Text\": \"Armand\", \"Bool\": true}," + LINE_SEPARATOR +
            "{\"Text\": \"Margot\", \"Bool\": false}," + LINE_SEPARATOR +
            "{\"Text\": \"Patrick\", \"Bool\": false}," + LINE_SEPARATOR +
            "{\"Text\": \"Alphonse\", \"Bool\": false}," + LINE_SEPARATOR +
            "{\"Text\": \"Louisa\", \"Bool\": true}," + LINE_SEPARATOR +
            "{\"Text\": \"Caryn\", \"Bool\": false}" + LINE_SEPARATOR +
            "]");
    }

    @Test
    void testGenerateFromSchemaWithLimit() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Object, ?> schema = Schema.of(
            field("Text", () -> faker.name().firstName()),
            field("Bool", () -> faker.bool().bool())
        );

        JsonTransformer<Object> transformer = JsonTransformer.builder().build();
        String json = transformer.generate(schema, 2);
        String expected = "[" + LINE_SEPARATOR +
            "{\"Text\": \"Willis\", \"Bool\": false}," + LINE_SEPARATOR +
            "{\"Text\": \"Carlena\", \"Bool\": true}" + LINE_SEPARATOR +
            "]";

        assertThat(json).isEqualTo(expected);
    }

    @Test
    @SuppressWarnings("removal")
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

        String expected = "[" + LINE_SEPARATOR +
            "{\"Number\": 3, \"Password\": \"nf3\"}" + LINE_SEPARATOR +
            "{\"Number\": 6, \"Password\": \"4b0v69\"}" + LINE_SEPARATOR +
            "{\"Number\": 7, \"Password\": \"00827v2\"}" + LINE_SEPARATOR +
            "{\"Number\": 1, \"Password\": \"5\"}" + LINE_SEPARATOR +
            "{\"Number\": 3, \"Password\": \"p6x\"}" + LINE_SEPARATOR +
            "]";

        assertThat(json).isEqualTo(expected);
    }

    @Test
    @SuppressWarnings("removal")
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

        String expected = "[" + LINE_SEPARATOR +
            "{\"Number\": 3, \"Password\": \"nf3\"}," + LINE_SEPARATOR +
            "{\"Number\": 6, \"Password\": \"4b0v69\"}," + LINE_SEPARATOR +
            "{\"Number\": 7, \"Password\": \"00827v2\"}," + LINE_SEPARATOR +
            "{\"Number\": 1, \"Password\": \"5\"}," + LINE_SEPARATOR +
            "{\"Number\": 3, \"Password\": \"p6x\"}" + LINE_SEPARATOR +
            "]";

        assertThat(json).isEqualTo(expected);
    }

    @Test
    @SuppressWarnings("removal")
    void testGenerateFromFakeSequenceStream() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Integer, ?> schema = Schema.of(
            field("Number", integer -> integer),
            field("Password", integer -> faker.internet().password(integer, integer))
        );

        JsonTransformer<Integer> transformer = JsonTransformer.<Integer>builder().withCommaBetweenObjects(false).build();
        FakeSequence<Integer> fakeSequence = faker.<Integer>stream()
            .suppliers(() -> faker.number().randomDigit())
            .len(2)
            .build();

        String json = transformer.generate(fakeSequence, schema);

        String expected = "[" + LINE_SEPARATOR +
            "{\"Number\": 3, \"Password\": \"0p4\"}" + LINE_SEPARATOR +
            "{\"Number\": 8, \"Password\": \"714487nf\"}" + LINE_SEPARATOR +
            "]";

        assertThat(json).isEqualTo(expected);
    }

    @Test
    @SuppressWarnings("removal")
    void testGenerateFromInfiniteFakeSequence() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Integer, ?> schema = Schema.of(
            field("Number", integer -> integer),
            field("Password", integer -> faker.internet().password(integer, integer))
        );

        JsonTransformer<Integer> transformer = JsonTransformer.<Integer>builder().build();
        FakeSequence<Integer> fakeSequence = faker.<Integer>stream()
            .suppliers(() -> faker.number().randomDigit())
            .build();

        assertThatThrownBy(() -> transformer.generate(fakeSequence, schema))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The sequence should be finite of size: FakeStream{minLength=-1, maxLength=-1, nullRate=0.0}");
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
        JsonTransformer<String> transformer = JsonTransformer.<String>builder().build();

        assertThat(transformer.generate(schema, 2).replaceAll(System.lineSeparator(), ""))
            .isEqualTo("[" + expected + "," + expected + "]");
    }

    @ParameterizedTest
    @MethodSource("generateTestSchema")
    void outputWithoutCommaForJsonTransformer(
        Schema<String, String> schema, String expected) {
        JsonTransformer<String> transformer = JsonTransformer.<String>builder().withCommaBetweenObjects(false).build();

        assertThat(transformer.generate(schema, 2).replaceAll(System.lineSeparator(), ""))
            .isEqualTo("[" + expected + expected + "]");
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
            of(Schema.of(field("number", () -> 123.123)), "{\"number\": 123.123}"),
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
