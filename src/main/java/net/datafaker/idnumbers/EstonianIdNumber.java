package net.datafaker.idnumbers;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import net.datafaker.providers.base.PersonIdNumber.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.gender;
import static net.datafaker.idnumbers.Utils.randomGender;

/**
 * Estonian personal identification number ("Isikukood" in estonian)
 * <p>
 * The number is 11 digits, with modulus 11 checksum digit.
 * There is fixed list of valid first digits to signify gender and birth century
 * <p>
 * and <a href="https://en.wikipedia.org/wiki/National_identification_number#Estonia">Estonian identification number</a>
 */
@InternalApi
public class EstonianIdNumber implements IdNumberGenerator {
    private static final DateTimeFormatter BIRTHDAY_FORMAT = DateTimeFormatter.ofPattern("yyMMdd");
    private static final int[] CHECKSUM_COEFFICIENTS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
    private static final int[] CHECKSUM_COEFFICIENTS2 = {3, 4, 5, 6, 7, 8, 9, 1, 2, 3};

    @Override
    public String countryCode() {
        return "EE";
    }

    @Override
    public String generateInvalid(final BaseProviders faker) {
        LocalDate birthday = faker.timeAndDate().birthday();
        String digits = basePart(faker, birthday, randomGender(faker));
        return digits + (checksum(digits) + 1) % 10;
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        LocalDate birthday = birthday(faker, request);
        Gender gender = gender(faker, request);
        String digits = basePart(faker, birthday, gender);
        String idNumber = digits + checksum(digits);
        return new PersonIdNumber(idNumber, birthday, gender);
    }

    private String basePart(BaseProviders faker, LocalDate birthday, Gender gender) {
        return firstDigit(birthday.getYear(), gender) +
            BIRTHDAY_FORMAT.format(birthday) +
            faker.number().digits(3);
    }

    @InternalApi
    static int firstDigit(int birthYear, Gender gender) {
        int digit = switch (birthYear / 100) {
            case 18 -> 1;
            case 19 -> 3;
            case 20 -> 5;
            case 21 -> 7;
            default -> throw new IllegalStateException("Birth year %s is out of allowed range [1800, 2199]".formatted(birthYear));
        };
        return switch (gender) {
            case FEMALE -> digit + 1;
            case MALE -> digit;
        };
    }

    @InternalApi
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
