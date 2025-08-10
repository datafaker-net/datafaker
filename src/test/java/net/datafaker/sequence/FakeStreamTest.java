package net.datafaker.sequence;

import net.datafaker.Faker;
import net.datafaker.providers.base.Address;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Name;
import net.datafaker.transformations.CompositeField;
import net.datafaker.transformations.CsvTransformer;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JsonTransformer;
import net.datafaker.transformations.Schema;
import net.datafaker.transformations.Transformer;
import net.datafaker.transformations.XmlTransformer;
import net.datafaker.transformations.sql.SqlTransformer;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.datafaker.transformations.Field.compositeField;
import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FakeStreamTest {
    private final Faker faker = new Faker();

    @Test
    void generateFiniteStream() {
        Stream<String> finiteNames = faker.<String>stream()
            .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
            .minLen(3)
            .maxLen(5)
            .generate();
        assertThat(finiteNames).hasSizeBetween(3, 5);
    }

    @Test
    void generateInfiniteStream() {
        Stream<String> infiniteNames = faker.stream(() -> faker.name().firstName())
            .generate();
        assertThat(infiniteNames.spliterator().estimateSize()).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    void generateStreamOfNames() {
        Stream<String> names = faker.stream(() -> faker.name().firstName())
            .len(5)
            .generate();
        names.forEach(name -> assertThat(name).matches("[a-zA-Z']+"));
    }

    @Test
    void generateStreamOfDigits() {
        Stream<String> digitsInfinite = faker.stream(() -> faker.number().digit())
            .generate();
        digitsInfinite.limit(1_000).forEach(name -> assertThat(name).matches("\\d"));
    }

    @Test
    void generateNullStream() {
        Stream<String> names = faker.<String>stream()
            .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
            .nullRate(1d)
            .len(10)
            .generate();
        names.forEach(name -> assertThat(name).isNull());
    }

    @ParameterizedTest
    @ValueSource(doubles = {Long.MIN_VALUE, Integer.MIN_VALUE, -1, -0.3, 2, 3, Integer.MAX_VALUE, Double.MAX_VALUE})
    void illegalNullRate(double nullRate) {
        assertThatThrownBy(
            () -> faker.stream()
                .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
                .nullRate(nullRate)
                .minLen(3)
                .maxLen(5)
                .generate())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Null rate should be between 0 and 1 (received: " + nullRate + ")");
    }

    @Test
    void generateStreamPassingSuppliersAsList() {
        BaseFaker faker = new BaseFaker();
        List<Supplier<String>> suppliers = List.of(() -> faker.name().firstName(), () -> faker.name().lastName());
        Stream<String> stream = faker.stream(suppliers).len(3).generate();

        assertThat(stream.collect(Collectors.toList())).hasSize(3);
    }

    @Test
    void generateStreamWithRepeatableFaker() {
        BaseFaker seededFaker = new BaseFaker(new Random(10L));

        Stream<String> names = faker.<String>stream()
            .faker(seededFaker)
            .suppliers(() -> seededFaker.name().firstName(), () -> seededFaker.name().lastName())
            .minLen(1)
            .maxLen(20)
            .generate();

        List<String> namesList = names.collect(Collectors.toList());
        assertThat(namesList).hasSize(14);
        assertThat(namesList.get(0)).isEqualTo("Flor");
        assertThat(namesList.get(1)).isEqualTo("Brian");
    }

    @Test
    void generateStreamWithDifferentObjects() {
        Stream<Object> objects = faker.stream()
            .suppliers(() -> faker.name().firstName(), () -> faker.random().nextInt(100))
            .maxLen(5)
            .generate();

        objects.forEach(object -> assertThat(object).isInstanceOfAny(Integer.class, String.class));
    }

    @Test
    void checkWrongArguments() {
        assertThatThrownBy(() ->
            faker.stream()
                .suppliers(() -> faker.name().firstName())
                .minLen(10)
                .maxLen(5)
                .generate())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Max length (5) must be not less than min length (10) and not negative");
    }

    @RepeatedTest(10)
    void singletonTest() {
        int limit = 10;
        assertThat(faker.stream()
            .minLen(limit)
            .maxLen(limit)
            .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
            .build()
            .singleton()
        ).isNotNull();
    }

    @Test
    void isInfiniteTest() {
        FakeSequence<String> infiniteNames = faker.stream(() -> faker.name().firstName())
            .build();
        assertThat(infiniteNames.isInfinite()).isTrue();

        FakeSequence<String> finiteNames = faker.stream(() -> faker.name().firstName())
            .len(5)
            .build();
        assertThat(finiteNames.isInfinite()).isFalse();
    }

    @Test
    void toCsv() {
        String separator = "$$$";
        int limit = 5;
        FakeSequence<Data> stream = faker.<Data>stream()
            .minLen(limit)
            .maxLen(limit)
            .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
            .build();

        CsvTransformer<Data> csvTransformer = CsvTransformer
            .<Data>builder().header(true).separator(separator).build();
        String csv = csvTransformer.generate(stream,
            Schema.of(
                field("name", Data::name),
                field("value", Data::value),
                field("range", Data::range),
                field("unit", Data::unit)));

        int numberOfLines = 0;
        int numberOfSeparator = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            } else if (csv.regionMatches(i, separator, 0, separator.length())) {
                numberOfSeparator++;
            }
        }

        assertThat(numberOfLines).isEqualTo(5);
        assertThat(numberOfSeparator).isEqualTo(18); // number of lines * (number of columns - 1)
    }

    @Test
    void toCsvFromInfiniteSequence() {
        String separator = "$$$";
        FakeSequence<Data> infiniteStream = faker.<Data>stream()
            .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
            .build();

        assertThatThrownBy(() ->
            CsvTransformer.<Data>builder().separator(separator).build()
                .generate(infiniteStream,
                    Schema.of(
                        field("name", Data::name),
                        field("value", Data::value),
                        field("range", Data::range),
                        field("unit", Data::unit)))
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The sequence should be finite of size: FakeStream{minLength=-1, maxLength=-1, nullRate=0.0}");
    }

    @Test
    void toJson() {
        int limit = 10;
        FakeSequence<Data> stream = faker.<Data>collection()
            .minLen(limit)
            .maxLen(limit)
            .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
            .build();

        JsonTransformer<Data> transformer = JsonTransformer.<Data>builder().build();

        String json = transformer.generate(stream, Schema.of(
            field("name", Data::name),
            field("value", Data::value),
            field("range", Data::range),
            field("unit", Data::unit)
        ));

        int numberOfLines = 0;
        for (int i = 0; i < json.length(); i++) {
            if (json.regionMatches(i, "},", 0, "},".length())) {
                numberOfLines++;
            }
        }

        assertThat(limit - 1).isEqualTo(numberOfLines); // limit - 1 since for the last line there is no comma
    }

    @Test
    void toJsonFromInfiniteSequence() {
        FakeSequence<Data> infiniteStream = faker.<Data>stream()
            .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
            .build();

        assertThatThrownBy(() ->
            JsonTransformer.<Data>builder().build()
                .generate(infiniteStream,
                    Schema.of(
                        field("name", Data::name),
                        field("value", Data::value),
                        field("range", Data::range),
                        field("unit", Data::unit)))
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The sequence should be finite of size: FakeStream{minLength=-1, maxLength=-1, nullRate=0.0}");
    }

    @Test
    void toNestedJson() {
        final int limit = 3;
        JsonTransformer<Name> transformer = JsonTransformer.<Name>builder().build();

        FakeSequence<CompositeField<Address, String>> secondaryAddresses =
            faker.<CompositeField<Address, String>>collection()
                .suppliers(() ->
                    compositeField(null,
                        field("country", () -> faker.address().country()),
                        field("city", () -> faker.address().city()),
                        field("zipcode", () -> faker.address().zipCode()),
                        field("streetAddress", () -> faker.address().streetAddress())
                    )
                )
                .maxLen(1)
                .minLen(1)
                .build();

        String json = transformer.generate(
            faker.<Name>stream()
                .suppliers(faker::name)
                .maxLen(limit)
                .minLen(limit)
                .build(),
            Schema.<Name, Object>of(
                compositeField("primaryAddress",
                    field("country", () -> faker.address().country()),
                    field("city", () -> faker.address().city()),
                    field("zipcode", () -> faker.address().zipCode()),
                    field("streetAddress", () -> faker.address().streetAddress())
                ),
                field("secondaryAddresses", secondaryAddresses::get),
                field("phones", name -> faker.<String>collection().suppliers(() -> faker.phoneNumber().phoneNumber()).maxLen(3).build().get())
            ));

        int numberOfLines = 0;
        for (int i = 0; i < json.length(); i++) {
            if (json.regionMatches(i, "}," + System.lineSeparator(), 0, ("}," + System.lineSeparator()).length())) {
                numberOfLines++;
            }
        }
        assertThat(numberOfLines).isEqualTo(limit - 1); // limit - 1 since for the last line there is no comma
    }

    @Test
    void testIterator() {
        int fakeSequenceSize = 100;
        FakeSequence<String> digits = faker
            .stream(() -> faker.number().digit())
            .len(fakeSequenceSize)
            .build();

        int count = 0;
        for (String digit : digits) {
            assertThat(digit).matches("\\d");
            count++;
        }

        assertThat(count).isEqualTo(fakeSequenceSize);
    }

    @Test
    void testIteratorInfinite() {
        FakeSequence<String> digits = faker
            .stream(() -> faker.number().digit())
            .build();

        assertThat(digits.isInfinite()).isTrue();

        int count = 0;
        int amountOfElementsToTake = 1_000;
        for (String digit : digits) {
            assertThat(digit).matches("\\d");
            count++;
            if (count == amountOfElementsToTake) {
                break;
            }
        }

        assertThat(count).isEqualTo(amountOfElementsToTake);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("inputForFilesCreatedTest")
    void testCsvFilesCreated(final String format, final Transformer<Object, CharSequence> transformer, final List<String> expected) {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Name name = faker.name();
        Schema<Object, ?> schema = Schema.of(
            field("FirstName", name::firstName),
            field("LastName", name::lastName)
        );

        File csvFile = Files.newTemporaryFile();
        assertThat(csvFile).exists().isEmpty();

        try (BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(csvFile))) {
            transformer.writeToOutputStream(fos, schema, 2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertThat(Files.linesOf(csvFile, StandardCharsets.UTF_8))
            .as(() -> format + " generator output")
            .containsAll(expected);
    }

    private static Stream<Arguments> inputForFilesCreatedTest() {
        return Stream.of(
            Arguments.of("xml", new XmlTransformer.XmlTransformerBuilder().build(), List.of(
                "<FirstName>Willis</FirstName><LastName>Huels</LastName>",
                "<FirstName>Carlena</FirstName><LastName>Jenkins</LastName>")),
            Arguments.of("csv", CsvTransformer.builder().build(), List.of(
                "\"FirstName\";\"LastName\"",
                "\"Willis\";\"Huels\"",
                "\"Carlena\";\"Jenkins\"")),
            Arguments.of("sql", SqlTransformer.builder().build(), List.of(
                "INSERT INTO \"MyTable\" (\"FirstName\", \"LastName\") VALUES ('Willis', 'Huels');",
                "INSERT INTO \"MyTable\" (\"FirstName\", \"LastName\") VALUES ('Carlena', 'Jenkins');"
            )),
            Arguments.of("json", JsonTransformer.builder().build(), List.of(
                "[",
                "{\"FirstName\": \"Willis\", \"LastName\": \"Huels\"},",
                "{\"FirstName\": \"Carlena\", \"LastName\": \"Jenkins\"}",
                "]"
            ))
        );
    }
}
