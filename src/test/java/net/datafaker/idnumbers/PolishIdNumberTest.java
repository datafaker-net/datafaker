package net.datafaker.idnumbers;

import net.datafaker.Faker;
import net.datafaker.idnumbers.PolishIdNumber.Gender;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Locale;

import static java.lang.Integer.parseInt;
import static net.datafaker.helpers.IdNumberPatterns.POLISH;
import static net.datafaker.providers.base.IdNumber.GenderRequest.ANY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PolishIdNumberTest {

    private static final Faker faker = new Faker(new Locale("pl", "PL"));
    private static final PolishIdNumber peselNumber = new PolishIdNumber();

    @RepeatedTest(100)
    void idNumberConsistsOf11Digits() {
        PersonIdNumber actual = peselNumber.generateValid(faker, new IdNumberRequest(0, 222, ANY));

        assertThat(actual.idNumber()).matches(POLISH);
        assertThat(year(actual.idNumber()) % 100).isEqualTo(actual.birthDate().getYear() % 100);
        assertThat(day(actual.idNumber())).isEqualTo(actual.birthDate().getDayOfMonth());
        assertControlDigit(actual.idNumber());
    }

    @Test
    @SuppressWarnings("deprecation")
    void generateIdNumberForFemale() {
        LocalDate birthDate = new BaseFaker().timeAndDate().birthday(0, 100);
        String actual = peselNumber.get(faker, birthDate, Gender.FEMALE);

        assertThat(getGenderDigit(actual) % 2).isZero();
    }

    @Test
    @SuppressWarnings("deprecation")
    void generateIdNumberForMale() {
        LocalDate birthDate = new BaseFaker().timeAndDate().birthday(0, 100);
        String actual = peselNumber.get(faker, birthDate, Gender.MALE);

        assertThat(getGenderDigit(actual) % 2).isOne();
    }

    @Test
    void centuryIsEncodedInMonthNumber() {
        assertThat(month(bornAt(6, 1800))).isEqualTo(86);
        assertThat(month(bornAt(6, 1801))).isEqualTo(86);
        assertThat(month(bornAt(6, 1899))).isEqualTo(86);
        assertThat(month(bornAt(3, 1900))).isEqualTo(3);
        assertThat(month(bornAt(3, 1950))).isEqualTo(3);
        assertThat(month(bornAt(3, 1999))).isEqualTo(3);
        assertThat(month(bornAt(5, 2000))).isEqualTo(25);
        assertThat(month(bornAt(5, 2001))).isEqualTo(25);
        assertThat(month(bornAt(5, 2050))).isEqualTo(25);
        assertThat(month(bornAt(5, 2099))).isEqualTo(25);
        assertThat(month(bornAt(5, 2100))).isEqualTo(45);
        assertThat(month(bornAt(5, 2199))).isEqualTo(45);
        assertThat(month(bornAt(5, 2200))).isEqualTo(65);
        assertThat(month(bornAt(5, 2299))).isEqualTo(65);
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, 1699, 1799})
    @SuppressWarnings("deprecation")
    void tooEarlyCenturies(int birthYear) {
        LocalDate birthDate = LocalDate.of(birthYear, 6, 28);

        assertThatThrownBy(() -> peselNumber.get(faker, birthDate, Gender.ANY))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Year is before 1800: %s", birthYear);
    }

    @ParameterizedTest
    @ValueSource(ints = {2300, 2399, 9999})
    @SuppressWarnings("deprecation")
    void tooLateCenturies(int birthYear) {
        LocalDate birthDate = LocalDate.of(birthYear, 6, 28);

        assertThatThrownBy(() -> peselNumber.get(faker, birthDate, Gender.ANY))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Year is after 2300: %s", birthYear);
    }

    private int getGenderDigit(String idNumber) {
        return idNumber.charAt(9) - '0';
    }

    @SuppressWarnings("deprecation")
    private String bornAt(int month, int year) {
        LocalDate birthDate = LocalDate.of(year, month, 28);
        return peselNumber.get(faker, birthDate, Gender.ANY);
    }

    private int year(String idNumber) {
        return parseInt(idNumber.substring(0, 2));
    }

    private int month(String idNumber) {
        return parseInt(idNumber.substring(2, 4));
    }

    private int day(String idNumber) {
        return parseInt(idNumber.substring(4, 6));
    }

    private void assertControlDigit(String gotPesel) {
        final int gotSum = (gotPesel.charAt(0) + gotPesel.charAt(4) + gotPesel.charAt(8) + gotPesel.charAt(10)
            + 3 * (gotPesel.charAt(1) + gotPesel.charAt(5) + gotPesel.charAt(9))
            + 7 * (gotPesel.charAt(2) + gotPesel.charAt(6)) + 9 * (gotPesel.charAt(3) + gotPesel.charAt(7))) % 10;
        assertThat(gotSum).isZero();
    }
}
