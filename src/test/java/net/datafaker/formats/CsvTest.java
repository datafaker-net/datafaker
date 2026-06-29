package net.datafaker.formats;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Name;
import net.datafaker.sequence.FakeSequence;
import net.datafaker.transformations.CsvTransformer;
import net.datafaker.transformations.Schema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static net.datafaker.transformations.Field.field;
import static net.datafaker.transformations.Transformer.LINE_SEPARATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CsvTest {

    @Test
    void csvTestNew() {
        final BaseFaker faker = new BaseFaker();
        String separator = "@@@";
        int limit = 20;
        Schema<String, String> schema =
            Schema.of(
                field("first_name", () -> faker.name().firstName()),
                field("last_name", () -> faker.name().lastName()),
                field("address", () -> faker.address().streetAddress()));
        CsvTransformer<String> transformer =
            CsvTransformer.<String>builder().header(true).separator(separator).build();

        String csv = transformer.generate(schema, limit);
        int numberOfLines = 0;
        int numberOfSeparator = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, LINE_SEPARATOR, 0, LINE_SEPARATOR.length())) {
                numberOfLines++;
            } else if (csv.regionMatches(i, separator, 0, separator.length())) {
                numberOfSeparator++;
            }
        }

        assertThat(limit).isEqualTo(numberOfLines);
        assertThat((limit + 1) * 2)
            .isEqualTo(numberOfSeparator); // number of lines * (number of columns - 1)*/
    }

    @Test
    void csvTestWithQuotesNew() {
        String separator = "$$$";
        int limit = 20;
        final BaseFaker faker = new BaseFaker();
        Schema<String, String> schema =
            Schema.of(
                field("first_name", () -> faker.expression("#{Name.first_name}")),
                field("last_name", () -> faker.expression("#{Name.last_name}")));
        CsvTransformer<String> transformer =
            CsvTransformer.<String>builder().header(true).separator(separator).build();

        String csv = transformer.generate(schema, limit);
        int numberOfLines = 0;
        int numberOfSeparator = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, LINE_SEPARATOR, 0, LINE_SEPARATOR.length())) {
                numberOfLines++;
            } else if (csv.regionMatches(i, separator, 0, separator.length())) {
                numberOfSeparator++;
            }
        }

        assertThat(limit).isEqualTo(numberOfLines);
        assertThat((limit + 1) * (schema.getFields().length - 1))
            .isEqualTo(numberOfSeparator); // number of lines * (number of columns - 1)
    }

    @Test
    void testCsvWithCommaNew() {
        Schema<Object, ? extends CharSequence> schema =
            Schema.of(field("values", () -> "1,2,3"), field("title", () -> "The \"fabulous\" artist"));
        CsvTransformer<Object> transformer =
            CsvTransformer.builder().header(true).separator(",").build();

        String csv = transformer.generate(schema, 1);

        String expected =
            "\"values\",\"title\"" + LINE_SEPARATOR
                + "\"1,2,3\",\"The \"\"fabulous\"\" artist\"";

        assertThat(csv).isEqualTo(expected);
    }

    @Test
    void testCsvWithDifferentObjects() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Object, ?> schema = Schema.of(
            field("Number", () -> faker.number().randomDigit()),
            field("Bool", () -> faker.bool().bool()),
            field("String", () -> faker.name().firstName()),
            field("Text", () -> "The, \"fabulous\" artist'")
        );
        CsvTransformer<Object> transformer =
            CsvTransformer.builder().header(true).separator(",").build();

        String csv = transformer.generate(schema, 4);

        String expected =
            "\"Number\",\"Bool\",\"String\",\"Text\"" + LINE_SEPARATOR
                + "3,false,\"Flor\",\"The, \"\"fabulous\"\" artist'\"" + LINE_SEPARATOR
                + "6,true,\"Stephnie\",\"The, \"\"fabulous\"\" artist'\"" + LINE_SEPARATOR
                + "1,false,\"Edythe\",\"The, \"\"fabulous\"\" artist'\"" + LINE_SEPARATOR
                + "1,true,\"Dwight\",\"The, \"\"fabulous\"\" artist'\"";

        assertThat(csv).isEqualTo(expected);
    }

    @Test
    @SuppressWarnings("removal")
    void testCsvWithDifferentObjectsFunction() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Integer, ?> schema = Schema.of(
            field("Number", integer -> integer),
            field("Password", integer -> faker.internet().password(integer, integer))
        );
        CsvTransformer<Integer> transformer =
            CsvTransformer.<Integer>builder().header(true).separator(",").build();

        FakeSequence<Integer> fakeSequence = faker.<Integer>collection()
            .suppliers(() -> faker.number().randomDigit())
            .len(5)
            .build();
        String csv = transformer.generate(fakeSequence, schema);

        String expected =
            "\"Number\",\"Password\"" + LINE_SEPARATOR
                + "3,\"nf3\"" + LINE_SEPARATOR
                + "6,\"4b0v69\"" + LINE_SEPARATOR
                + "7,\"00827v2\"" + LINE_SEPARATOR
                + "1,\"5\"" + LINE_SEPARATOR
                + "3,\"p6x\"";

        assertThat(csv).isEqualTo(expected);
    }

    @Test
    @SuppressWarnings("removal")
    void testCsvWithDifferentObjectsFunctionStream() {
        BaseFaker faker = new BaseFaker(new Random(10L));
        Schema<Integer, ?> schema = Schema.of(
            field("Number", integer -> integer),
            field("Password", integer -> faker.internet().password(integer, integer))
        );
        CsvTransformer<Integer> transformer =
            CsvTransformer.<Integer>builder().header(true).separator(",").build();

        FakeSequence<Integer> fakeSequence = faker.<Integer>stream()
            .suppliers(() -> faker.number().randomDigit())
            .len(3)
            .build();

        String csv = transformer.generate(fakeSequence, schema);

        String expected =
            "\"Number\",\"Password\"" + LINE_SEPARATOR
                + "3,\"0p4\"" + LINE_SEPARATOR
                + "8,\"714487nf\"" + LINE_SEPARATOR
                + "5,\"0v691\"";

        assertThat(csv).isEqualTo(expected);
    }

    @Test
    void testCsvWithInfiniteSequence() {
        BaseFaker faker = new BaseFaker(new Random(10L));

        Schema<Integer, ?> schema = Schema.of(
            field("Number", integer -> faker.number().digits(integer)),
            field("String", prefix -> prefix + ": " + faker.name().firstName())
        );
        CsvTransformer<Integer> transformer =
            CsvTransformer.<Integer>builder().header(true).separator(",").build();

        FakeSequence<Integer> fakeSequence = faker.<Integer>stream()
            .suppliers(() -> faker.number().randomDigit())
            .build();
        assertThatThrownBy(() -> transformer.generate(fakeSequence, schema))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The sequence should be finite of size: FakeStream{minLength=-1, maxLength=-1, nullRate=0.0}");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 3, 10, 20, 100})
    void testLimitForCsv(int limit) {
        final BaseFaker faker = new BaseFaker();
        String csv = CsvTransformer.<Name>builder().separator(" : ").header(false).build()
            .generate(faker.<Name>collection().suppliers(faker::name).maxLen(limit + 1).build(),
                Schema.of(field("firstName", Name::firstName), field("lastname", Name::lastName)));

        int numberOfLines = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, LINE_SEPARATOR, 0, LINE_SEPARATOR.length())) {
                numberOfLines++;
            }
        }

        assertThat(numberOfLines).isEqualTo(limit);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 3, 10, 20, 100})
    void testLimitForCsvNew(int limit) {
        final BaseFaker faker = new BaseFaker();
        Schema<Name, String> schema =
            Schema.of(field("firstName", Name::firstName), field("lastname", Name::lastName));

        CsvTransformer<Name> transformer =
            CsvTransformer.<Name>builder().header(false).separator(",").build();
        String csv =
            transformer.generate(
                faker.<Name>collection().suppliers(faker::name).maxLen(limit + 1).build(),
                schema);

        int numberOfLines = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, LINE_SEPARATOR, 0, LINE_SEPARATOR.length())) {
                numberOfLines++;
            }
        }

        assertThat(numberOfLines).isEqualTo(limit);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 3, 10, 20})
    void testLimitForCollection(int limit) {
        final BaseFaker faker = new BaseFaker();
        String csv = CsvTransformer.<Name>builder().header(false).build()
            .generate(faker.<Name>collection().suppliers(faker::name).maxLen(limit).build(),
                Schema.of(field("firstName", Name::firstName), field("lastName", Name::lastName)));

        int numberOfLines = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, LINE_SEPARATOR, 0, LINE_SEPARATOR.length())) {
                numberOfLines++;
            }
        }

        assertThat(numberOfLines == 0 ? 0 : numberOfLines + 1).isEqualTo(limit);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 3, 10, 20, 100})
    void testLimitForCollectionNew(int limit) {
        final BaseFaker faker = new BaseFaker();
        Schema<Name, String> schema =
            Schema.of(field("firstName", Name::firstName), field("lastname", Name::lastName));

        CsvTransformer<Name> transformer =
            CsvTransformer.<Name>builder().header(false).separator(" : ").build();
        String csv =
            transformer.generate(
                faker.<Name>collection().suppliers(faker::name).maxLen(limit + 1).build(),
                schema);

        int numberOfLines = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, LINE_SEPARATOR, 0, LINE_SEPARATOR.length())) {
                numberOfLines++;
            }
        }

        assertThat(numberOfLines).isEqualTo(limit);
    }

    @Test
    void testInfiniteCsv() {
        final BaseFaker faker = new BaseFaker();
        FakeSequence<Name> infiniteSequence = faker.<Name>stream()
            .suppliers(faker::name)
            .build();

        assertThatThrownBy(() ->
            CsvTransformer.<Name>builder().separator(" : ").header(false).build()
                .generate(infiniteSequence,
                    Schema.of(field("firstName", Name::firstName), field("lastName", Name::lastName)))
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The sequence should be finite of size: FakeStream{minLength=-1, maxLength=-1, nullRate=0.0}");
    }

    @Test
    void testInfiniteCsvWithLimit() {
        int limit = 10;
        final BaseFaker faker = new BaseFaker();
        FakeSequence<Name> infiniteSequence = faker.<Name>stream()
            .suppliers(faker::name)
            .len(limit)
            .build();

        String csv = CsvTransformer.<Name>builder().header(false).separator(" : ").build()
            .generate(infiniteSequence,
                Schema.of(
                    field("firstName", Name::firstName),
                    field("lastName", Name::lastName)));

        int numberOfLines = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, LINE_SEPARATOR, 0, LINE_SEPARATOR.length())) {
                numberOfLines++;
            }
        }

        assertThat(numberOfLines + 1).isEqualTo(limit);
    }

    @Test
    void supplierShouldBeDefinedInCaseOfNullInput() {
        Schema<Name, String> schema =
            Schema.of(field("firstName", Name::firstName), field("lastname", Name::lastName));
        assertThatThrownBy(() ->
            CsvTransformer.<Name>builder()
                .header(false).separator(" : ")
                .build()
                .generate(schema, 1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Input could be null only if suppliers are defined");
    }
}
