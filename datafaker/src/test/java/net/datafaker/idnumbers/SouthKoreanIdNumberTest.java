package net.datafaker.idnumbers;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class SouthKoreanIdNumberTest {

    private static final DateTimeFormatter YYMMDD = DateTimeFormatter.ofPattern("yyMMdd");
    private static final Pattern D_6_D_7 = Pattern.compile("\\d{6}-\\d{7}");

    @RepeatedTest(100)
    void testValidKoKrRrn() {
        Faker f = new Faker(new Locale("en", "KR"));
        String rrn = f.idNumber().valid();

        // Check if contains other character than digit
        assertThat(rrn).matches(D_6_D_7);
        // Check date
        LocalDate date = LocalDate.parse(rrn.substring(0, 6), YYMMDD);
        int currentYear = LocalDate.now().getYear();
        assertThat(date.getYear()).isBetween(currentYear - 80, currentYear + 80);
    }

}
