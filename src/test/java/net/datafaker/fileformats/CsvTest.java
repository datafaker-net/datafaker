package net.datafaker.fileformats;

import net.datafaker.AbstractFakerTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvTest extends AbstractFakerTest {

    @Test
    public void csvTest() {
        String separator = "@@@";
        int limit = 20;
        String csv = new Csv.CsvBuilder()
            .columns(Csv.Column.of("first_name", () -> faker.name().firstName()),
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

        assertEquals(limit + 1, numberOfLines); // limit + 1 line for header
        assertEquals((limit + 1) * 2, numberOfSeparator); // number of lines * (number of columns - 1)
    }

    @Test
    public void csvTestWithQuotes() {
        String separator = "$$$";
        int limit = 20;
        Collection<Csv.Column> columns = new ArrayList<>();
        columns.add(Csv.Column.of("first_name", () -> faker.expression("#{Name.first_name}")));
        columns.add(Csv.Column.of("last_name", () -> faker.expression("#{Name.last_name}")));
        String csv = new Csv.CsvBuilder()
            .columns(columns)
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

        assertEquals(limit + 1, numberOfLines); // limit + 1 line for header
        assertEquals((limit + 1) * (columns.size() - 1), numberOfSeparator); // number of lines * (number of columns - 1)
    }

    @Test
    public void testCsvWithComma() {
        String csv = new Csv.CsvBuilder()
            .columns(
                Csv.Column.of("values", () -> "1,2,3"),
                Csv.Column.of("title", () -> "The \"fabulous\" artist"))
            .header(true)
            .separator(",")
            .limit(1).build().get();

        String expected = "\"values\",\"title\"" + System.lineSeparator() +
            "\"1,2,3\",\"The \"\"fabulous\"\" artist\"" + System.lineSeparator();

        assertEquals(csv, expected);
    }
}
