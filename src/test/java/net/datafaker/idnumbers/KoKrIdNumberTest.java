package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseFaker;
import org.junit.jupiter.api.RepeatedTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class KoKrIdNumberTest {

    private static final DateTimeFormatter YYMMDD = DateTimeFormatter.ofPattern("yyMMdd");
    private static final Pattern D_6_D_7 = Pattern.compile("\\d{6}-\\d{7}");

    @RepeatedTest(100)
    void testValidKoKrRrn() {
        final BaseFaker f = new BaseFaker();
        String rrn = f.idNumber().validKoKrRrn();

        // Check if contains other character than digit
        assertThat(rrn).matches(D_6_D_7);
        try {
            // Check date
            LocalDate.parse(rrn.substring(0, 6), YYMMDD);
        } catch (DateTimeParseException e) {
            assertThat(e).isNull();
        }
    }
    
}
