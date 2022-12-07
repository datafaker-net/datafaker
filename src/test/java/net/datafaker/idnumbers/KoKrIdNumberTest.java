package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseFaker;
import org.junit.jupiter.api.RepeatedTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;

class KoKrIdNumberTest {

    @RepeatedTest(100)
    void testValidKoKrRrn() {
        final BaseFaker f = new BaseFaker();
        String rrn = f.idNumber().validKoKrRrn();

        // Check if contains other character than digit
        assertThat(rrn).matches("\\d{6}-\\d{7}");
        try {
            // Check date
            LocalDate.parse(rrn.substring(0, 6), DateTimeFormatter.ofPattern("yyMMdd"));
        } catch (DateTimeParseException e) {
            assertThat(e).isNull();
        }
    }
    
}
