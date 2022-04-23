package net.datafaker.fileformats;

import net.datafaker.AbstractFakerTest;
import net.datafaker.Faker;
import net.datafaker.Name;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvTest extends AbstractFakerTest {

    @Test
    public void csvTest() {
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
    public void csvTestWithQuotes() {
        String separator = "$$$";
        int limit = 20;
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
    public void testCsvWithComma() {
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

    @Test
    void test() {
        Faker faker = new Faker();
        String json = Format.toJson(
                faker.<Name>collection()
                    .suppliers(faker::name)
                    .maxLen(2)
                    .build())
            .set("firstName", Name::firstName)
            .set("lastName", Name::lastName)
            .build()
            .generate();
        System.out.println(json);
    }
}
