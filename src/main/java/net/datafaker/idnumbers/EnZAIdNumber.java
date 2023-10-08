package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

/**
 * Implementation based on the definition at
 * <a href="https://en.wikipedia.org/wiki/South_African_identity_card">https://en.wikipedia.org/wiki/South_African_identity_card</a>
 */

public class EnZAIdNumber implements IdNumbers {

    private static final String[] VALID_PATTERN = {"##########08#", "##########18#"};
    private static final String[] CODE_PATTERN = {"18", "08"};

    /**
     * Generate a valid social security number on faker
     *
     * @param f the java-faker
     * @return a valid social security number on faker
     */
    public String getValidSsn(BaseProviders f) {
        String candidate = DATE_TIME_FORMATTER.format(f.date().birthdayLocalDate(0, 100))
            + f.numerify("####")
            + f.options().option(CODE_PATTERN);
        return candidate + calculateChecksum(candidate, 12);
    }

    /**
     * Generate a invalid social security number on faker
     *
     * @param f the java-faker
     * @return a invalid social security number on faker
     */
    public String getInValidSsn(BaseProviders f) {

        String ssn = f.numerify(f.options().option(VALID_PATTERN));
        while (isValidEnZASsn(ssn)) {
            String pattern = getPattern(f);
            ssn = f.numerify(pattern);
        }
        return ssn;
    }

    /**
     * Generate a fixed format numeric string
     *
     * @param faker the java-faker
     * @return a fixed format numeric string
     */
    private String getPattern(BaseProviders faker) {
        return faker.options().option(VALID_PATTERN);
    }

    /**
     * Judge whether a social security number is valid
     *
     * @param ssn social security number
     */
    public static boolean isValidEnZASsn(String ssn) {
        if (ssn.length() != 13) {
            return false;
        }

        try {
            if (parseDate(ssn)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return ssn.charAt(12) - '0' == calculateChecksum(ssn, 12);
    }

    /**
     * Judge whether a numeric string of ssn can represent a legal date
     *
     * @param ssn social security number
     */
    private static boolean parseDate(String ssn) {
        if (ChronoField.YEAR.range().isValidIntValue(Integer.parseInt(ssn, 0, 2, 10))) {
            if (ChronoField.MONTH_OF_YEAR.range().isValidIntValue(Integer.parseInt(ssn, 2, 4, 10))) {
                if (ChronoField.DAY_OF_MONTH.range().isValidIntValue(Integer.parseInt(ssn, 4, 6, 10))) {
                    String dateString = ssn.substring(0, 6);
                    LocalDate date = LocalDate.parse(dateString, DATE_TIME_FORMATTER);
                    // want to check that the parsed date is equal to the supplied data, most of the attempts will fail
                    String reversed = date.format(DATE_TIME_FORMATTER);
                    return !reversed.equals(dateString);
                }
            }
        }
        return false;
    }

    /**
     * Calculate the Check Number in the last number of a ssn
     *
     * @param number a social security number not including the last number
     * @return check number of this ssn
     */
    private static int calculateChecksum(String number, int length2Check) {

        int totalNumber = 0;

        for (int i = length2Check - 1; i >= 0; i -= 2) {
            int tmpNumber = calculate((number.charAt(i) - '0') * 2);
            if (i == 0) {
                totalNumber += tmpNumber;
            } else {
                totalNumber += tmpNumber + number.charAt(i - 1) - '0';
            }
        }
        if (totalNumber >= 0 && totalNumber < 9) {
            return 10 - totalNumber;
        } else {
            int res = totalNumber % 10;
            return res == 0 ? res : 10 - res;
        }
    }

    /**
     * Calculate the sum of each digit of the number
     *
     * @return sum of each digit of the number
     */
    private static int calculate(int number) {
        int res = 0;
        while (number > 0) {
            res += number % 10;
            number /= 10;
        }
        return res;
    }

}
