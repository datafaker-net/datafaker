package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Estonian personal identification number ("Isikukood" in estonian)
 * <p>
 * The number is 11 digits, with modulus 11 checksum digit.
 * There is fixed list of valid first digits to signify gender and birth century
 * <p>
 * and <a href="https://en.wikipedia.org/wiki/National_identification_number#Estonia">Estonian identification number</a>
 */
public class EstonianIdNumber implements IdNumbers {
    private static final DateTimeFormatter BIRTHDAY_FORMAT = DateTimeFormatter.ofPattern("yyMMdd");
    private static final int[] CHECKSUM_COEFFICIENTS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
    private static final int[] CHECKSUM_COEFFICIENTS2 = {3, 4, 5, 6, 7, 8, 9, 1, 2, 3};

    @Override
    public String country() {
        return "EE";
    }

    public String generateInvalid(final BaseProviders faker) {
        String digits = basePart(faker);
        return digits + (checksum(digits) + 1) % 10;
    }

    public String generateValid(final BaseProviders faker) {
        String digits = basePart(faker);
        return digits + checksum(digits);
    }

    private String basePart(BaseProviders faker) {
        LocalDate birthday = faker.timeAndDate().birthday(0, 100);
        return firstDigit(faker) +
            BIRTHDAY_FORMAT.format(birthday) +
            faker.number().digits(3);
    }

    private int firstDigit(BaseProviders faker) {
        return faker.random().nextInt(1, 6);
    }

    static int checksum(String numbers) {
        int checksum = checksum(numbers, CHECKSUM_COEFFICIENTS);
        return checksum != 10 ? checksum : checksum(numbers, CHECKSUM_COEFFICIENTS2) % 10;
    }

    private static int checksum(String numbers, int[] checksumCoefficients) {
        int checkSum = 0;
        for (int i = 0; i < numbers.length(); i++) {
            int digit = Character.getNumericValue(numbers.charAt(i));
            checkSum += checksumCoefficients[i] * digit;
        }

        return checkSum % 11;
    }
}
