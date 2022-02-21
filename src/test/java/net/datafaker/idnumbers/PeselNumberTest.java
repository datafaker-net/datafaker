package net.datafaker.idnumbers;

import java.time.LocalDate;
import java.time.ZoneId;

import net.datafaker.Faker;
import net.datafaker.idnumbers.PeselNumber.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

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
        Assertions.assertThrows(IllegalArgumentException.class, () -> peselNumber.get(givenBirthDate, Gender.ANY));
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
        Assertions.assertNotNull(gotPesel);
        Assertions.assertEquals(PESEL_EXPECTED_LENGTH, gotPesel.length());
        Assertions.assertTrue(gotPesel.chars().allMatch(Character::isDigit));
    }

    private void assertBirthDate(LocalDate givenBirthDate, String gotPesel) {
        final int gotYear = toNumber(gotPesel.charAt(0), gotPesel.charAt(1));
        Assertions.assertEquals(givenBirthDate.getYear() % 100, gotYear);

        final int gotMonth = toNumber(gotPesel.charAt(2), gotPesel.charAt(3));

        final int givenYear = givenBirthDate.getYear();

        if (givenYear < 1800) {
            Assertions.fail("Year is before 1800. Test case is broken.");
        } else if (givenYear < 1900) {
            Assertions.assertEquals(givenBirthDate.getMonthValue() + 80, gotMonth);
        } else if (givenYear < 2000) {
            Assertions.assertEquals(givenBirthDate.getMonthValue(), gotMonth);
        } else if (givenYear < 2100) {
            Assertions.assertEquals(givenBirthDate.getMonthValue() + 20, gotMonth);
        } else if (givenYear < 2200) {
            Assertions.assertEquals(givenBirthDate.getMonthValue() + 40, gotMonth);
        } else if (givenYear < 2300) {
            Assertions.assertEquals(givenBirthDate.getMonthValue() + 60, gotMonth);
        } else {
            throw new IllegalArgumentException("Year is after 2300. Test case is broken.");
        }

        final int gotDay = toNumber(gotPesel.charAt(4), gotPesel.charAt(5));
        Assertions.assertEquals(givenBirthDate.getDayOfMonth(), gotDay);
    }

    private void assertGender(Gender givenGender, String gotPesel) {
        final int gotGenderDigit = gotPesel.charAt(9) - '0';
        switch (givenGender) {
            case FEMALE:
                Assertions.assertEquals(0, gotGenderDigit % 2);
                break;
            case MALE:
                Assertions.assertEquals(1, gotGenderDigit % 2);
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
        Assertions.assertEquals(0, gotSum);
    }

    private int toNumber(char digit2, char digit1) {
        return (digit2 - '0') * 10 + digit1 - '0';
    }
}
