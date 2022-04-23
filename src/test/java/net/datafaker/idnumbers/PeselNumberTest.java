package net.datafaker.idnumbers;

import java.time.LocalDate;
import java.time.ZoneId;

import net.datafaker.Faker;
import net.datafaker.idnumbers.PeselNumber.Gender;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.fail;

class PeselNumberTest {

    public static final int PESEL_EXPECTED_LENGTH = 11;

    private static PeselNumber peselNumber;

    @BeforeAll
    static void setUpBeforeClass() {
        peselNumber = new PeselNumber(Faker.instance());
    }

    @ParameterizedTest
    @EnumSource(value = Gender.class, names = {"MALE", "FEMALE"})
    void testGenderedPesel(Gender givenGender) {
        /*
         * Given
         */
        final LocalDate givenBirthDate = Faker.instance().date().birthday(0, 100).toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDate();
        /*
         * When
         */
        final String gotPesel = peselNumber.get(givenBirthDate, givenGender);

        /*
         * Then
         */
        assertBasics(gotPesel);
        assertBirthDate(givenBirthDate, gotPesel);
        assertGender(givenGender, gotPesel);
        assertControlDigit(gotPesel);
    }

    @ParameterizedTest
    @ValueSource(ints = {1850, 1950, 2050, 2150, 2250})
    void testCenturiesPesel(int givenBirthYear) {
        /*
         * Given
         */
        final LocalDate givenBirthDate = LocalDate.of(givenBirthYear, 6, 28);

        /*
         * When
         */
        final String gotPesel = peselNumber.get(givenBirthDate, Gender.ANY);

        /*
         * Then
         */
        assertBasics(gotPesel);
        assertBirthDate(givenBirthDate, gotPesel);
        assertControlDigit(gotPesel);
    }

    @ParameterizedTest()
    @ValueSource(ints = {1799, 2300})
    void testInvalidCenturiesPesel(int givenBirthYear) {
        /*
         * Given
         */
        final LocalDate givenBirthDate = LocalDate.of(givenBirthYear, 6, 28);

        /*
         * When
         */
        assertThatThrownBy(() -> peselNumber.get(givenBirthDate, Gender.ANY))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testNullGender() {
        /*
         * Given
         */
        final LocalDate givenBirthDate = LocalDate.now();

        /*
         * When
         */
        final String gotPesel = peselNumber.get(givenBirthDate, null);

        /*
         * Then
         */
        assertBasics(gotPesel);
        assertBirthDate(givenBirthDate, gotPesel);
        assertControlDigit(gotPesel);
    }

    private void assertBasics(String gotPesel) {
        assertThat(gotPesel).isNotNull();
        assertThat(PESEL_EXPECTED_LENGTH).isEqualTo(gotPesel.length());
        assertThat(gotPesel.chars().allMatch(Character::isDigit)).isTrue();
    }

    private void assertBirthDate(LocalDate givenBirthDate, String gotPesel) {
        final int gotYear = toNumber(gotPesel.charAt(0), gotPesel.charAt(1));
        assertThat(givenBirthDate.getYear() % 100).isEqualTo(gotYear);

        final int gotMonth = toNumber(gotPesel.charAt(2), gotPesel.charAt(3));

        final int givenYear = givenBirthDate.getYear();

        if (givenYear < 1800) {
            fail("Year is before 1800. Test case is broken.");
        } else if (givenYear < 1900) {
            assertThat(givenBirthDate.getMonthValue() + 80).isEqualTo(gotMonth);
        } else if (givenYear < 2000) {
            assertThat(givenBirthDate.getMonthValue()).isEqualTo(gotMonth);
        } else if (givenYear < 2100) {
            assertThat(givenBirthDate.getMonthValue() + 20).isEqualTo(gotMonth);
        } else if (givenYear < 2200) {
            assertThat(givenBirthDate.getMonthValue() + 40).isEqualTo(gotMonth);
        } else if (givenYear < 2300) {
            assertThat(givenBirthDate.getMonthValue() + 60).isEqualTo(gotMonth);
        } else {
            throw new IllegalArgumentException("Year is after 2300. Test case is broken.");
        }

        final int gotDay = toNumber(gotPesel.charAt(4), gotPesel.charAt(5));
        assertThat(givenBirthDate.getDayOfMonth()).isEqualTo(gotDay);
    }

    private void assertGender(Gender givenGender, String gotPesel) {
        final int gotGenderDigit = gotPesel.charAt(9) - '0';
        switch (givenGender) {
            case FEMALE:
                assertThat(gotGenderDigit % 2).isEqualTo(0);
                break;
            case MALE:
                assertThat(gotGenderDigit % 2).isEqualTo(1);
                break;
            default:
            case ANY:
                break;
        }
    }

    private void assertControlDigit(String gotPesel) {
        final int gotSum = (gotPesel.charAt(0) + gotPesel.charAt(4) + gotPesel.charAt(8) + gotPesel.charAt(10)
            + 3 * (gotPesel.charAt(1) + gotPesel.charAt(5) + gotPesel.charAt(9))
            + 7 * (gotPesel.charAt(2) + gotPesel.charAt(6)) + 9 * (gotPesel.charAt(3) + gotPesel.charAt(7))) % 10;
        assertThat(gotSum).isEqualTo(0);
    }

    private int toNumber(char digit2, char digit1) {
        return (digit2 - '0') * 10 + digit1 - '0';
    }
}
