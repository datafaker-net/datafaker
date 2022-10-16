package net.datafaker.fileformats;

import net.datafaker.AbstractFakerTest;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Name;
import net.datafaker.util.FakeSequence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CsvTest extends AbstractFakerTest {

    @Test
    void csvTest() {
        final BaseFaker faker = new BaseFaker();
        String separator = "@@@";
        int limit = 20;
        String csv = Format.toCsv(Csv.Column.of("first_name", () -> faker.name().firstName()),
                Csv.Column.of("last_name", () -> faker.name().lastName()),
                Csv.Column.of("address", () -> faker.address().streetAddress()))
            .header(true)
            .separator(separator)
            .limit(limit).build().get();
        int numberOfLines = 0;
        int numberOfSeparator = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            } else if (csv.regionMatches(i, separator, 0, separator.length())) {
                numberOfSeparator++;
            }
        }

        assertThat(limit + 1).isEqualTo(numberOfLines); // limit + 1 line for header
        assertThat((limit + 1) * 2).isEqualTo(numberOfSeparator); // number of lines * (number of columns - 1)
    }

    @Test
    void csvTestWithQuotes() {
        String separator = "$$$";
        int limit = 20;
        final BaseFaker faker = new BaseFaker();
        List<Csv.Column> columns = new ArrayList<>();
        columns.add(Csv.Column.of("first_name", () -> faker.expression("#{Name.first_name}")));
        columns.add(Csv.Column.of("last_name", () -> faker.expression("#{Name.last_name}")));
        String csv = Format.toCsv(columns)
            .header(true)
            .separator(separator)
            .quote('%')
            .limit(limit).build().get();
        int numberOfLines = 0;
        int numberOfSeparator = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            } else if (csv.regionMatches(i, separator, 0, separator.length())) {
                numberOfSeparator++;
            }
        }

        assertThat(limit + 1).isEqualTo(numberOfLines); // limit + 1 line for header
        assertThat((limit + 1) * (columns.size() - 1)).isEqualTo(numberOfSeparator); // number of lines * (number of columns - 1)
    }

    @Test
    void testCsvWithComma() {
        String csv = Format.toCsv(
                Csv.Column.of("values", () -> "1,2,3"),
                Csv.Column.of("title", () -> "The \"fabulous\" artist"))
            .header(true)
            .separator(",")
            .limit(1).build().get();

        String expected = "\"values\",\"title\"" + System.lineSeparator() +
            "\"1,2,3\",\"The \"\"fabulous\"\" artist\"" + System.lineSeparator();

        assertThat(csv).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 3, 10, 20, 100})
    void testLimitForCsv(int limit) {
        final BaseFaker faker = new BaseFaker();
        String csv = Format.toCsv(
                faker.<Name>collection()
                    .suppliers(faker::name)
                    .maxLen(limit + 1)
                    .build())
            .headers(() -> "firstName", () -> "lastname")
            .columns(Name::firstName, Name::lastName)
            .separator(" : ")
            .header(false)
            .limit(limit)
            .build().get();

        int numberOfLines = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            }
        }

        assertThat(numberOfLines).isEqualTo(limit);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 3, 10, 20, 100})
    void testLimitForCollection(int limit) {
        final BaseFaker faker = new BaseFaker();
        String csv = Format.toCsv(
                faker.<Name>collection()
                    .suppliers(faker::name)
                    .maxLen(limit)
                    .build())
            .headers(() -> "firstName", () -> "lastname")
            .columns(Name::firstName, Name::lastName)
            .separator(" : ")
            .header(false)
            .limit(limit + 1)
            .build().get();

        int numberOfLines = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
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
            Format.toCsv(infiniteSequence)
                .headers(() -> "firstName", () -> "lastname")
                .columns(Name::firstName, Name::lastName)
                .separator(" : ")
                .header(false)
                .build()
                .get()
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Trying to generate a CSV from infinite sequence");
    }

    @Test
    void testInfiniteCsvWithLimit() {
        int limit = 10;
        final BaseFaker faker = new BaseFaker();
        FakeSequence<Name> infiniteSequence = faker.<Name>stream()
            .suppliers(faker::name)
            .build();

        String csv = Format.toCsv(infiniteSequence)
            .headers(() -> "firstName", () -> "lastname")
            .columns(Name::firstName, Name::lastName)
            .separator(" : ")
            .header(false)
            .limit(limit)
            .build()
            .get();

        int numberOfLines = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            }
        }

        assertThat(numberOfLines).isEqualTo(limit);
    }

}
