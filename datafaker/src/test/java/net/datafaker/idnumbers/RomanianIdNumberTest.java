package net.datafaker.idnumbers;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static net.datafaker.providers.base.PersonIdNumber.Gender.FEMALE;
import static net.datafaker.providers.base.PersonIdNumber.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

class RomanianIdNumberTest {
    private static final Pattern RE_TWO_DIGITS = Pattern.compile("\\d{2}");
    private static final Pattern RE_THREE_DIGITS = Pattern.compile("\\d{3}");
    private static final Pattern RE_THIRTEEN_DIGITS = Pattern.compile("\\d{13}");

    private final RomanianIdNumber impl = new RomanianIdNumber();
    private final Faker faker = new Faker();

    @RepeatedTest(100)
    void sample() {
        assertThat(impl.generateValid(faker)).matches(RE_THIRTEEN_DIGITS);
    }

    @Test
    void firstDigit_18xx() {
        for (int year = 1800; year <= 1899; year++) {
            assertThat(impl.firstCharacter(LocalDate.of(year, 1, 1), MALE)).isEqualTo(3);
            assertThat(impl.firstCharacter(LocalDate.of(year, 1, 1), FEMALE)).isEqualTo(4);
        }
    }

    @Test
    void firstDigit_19xx() {
        for (int year = 1900; year <= 1999; year++) {
            assertThat(impl.firstCharacter(LocalDate.of(year, 1, 1), MALE)).isEqualTo(1);
            assertThat(impl.firstCharacter(LocalDate.of(year, 1, 1), FEMALE)).isEqualTo(2);
        }
    }

    @Test
    void firstDigit_20xx() {
        for (int year = 2000; year <= 2099; year++) {
            assertThat(impl.firstCharacter(LocalDate.of(year, 1, 1), MALE)).isEqualTo(5);
            assertThat(impl.firstCharacter(LocalDate.of(year, 1, 1), FEMALE)).isEqualTo(6);
        }
    }

    @Test
    void dateOfBirth() {
        assertThat(impl.dateOfBirth(LocalDate.of(1990, 1, 1))).isEqualTo("900101");
        assertThat(impl.dateOfBirth(LocalDate.of(1234, 12, 31))).isEqualTo("341231");
    }

    @Test
    void countyCode() {
        Set<String> allCodes = new HashSet<>(48);
        for (int i = 0; i < 10_000; i++) {
            String countyCode = impl.countyCode(faker);
            assertThat(countyCode).matches(RE_TWO_DIGITS);
            allCodes.add(countyCode);
        }

        assertThat(allCodes).hasSize(48);
        assertThat(allCodes).contains("01");
        assertThat(allCodes).contains("09");
        assertThat(allCodes).contains("10");
        assertThat(allCodes).contains("11");
        assertThat(allCodes).contains("19");
        assertThat(allCodes).contains("20");
        assertThat(allCodes).contains("21");
        assertThat(allCodes).contains("45");
        assertThat(allCodes).contains("46");
        assertThat(allCodes).contains("51");
        assertThat(allCodes).contains("52");
        assertThat(allCodes).doesNotContain("53");
        assertThat(allCodes).doesNotContain("47");
        assertThat(allCodes).doesNotContain("49");
        assertThat(allCodes).doesNotContain("50");
    }

    @RepeatedTest(10)
    void sequenceNumber() {
        assertThat(impl.sequenceNumber(faker)).matches(RE_THREE_DIGITS);
    }

    @Test
    void checksum() {
        assertThat(impl.checksum("198081945678")).isEqualTo(1);
        assertThat(impl.checksum("293052637289")).isEqualTo(4);
    }
}
