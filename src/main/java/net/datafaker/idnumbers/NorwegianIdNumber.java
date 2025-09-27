package net.datafaker.idnumbers;

import com.google.errorprone.annotations.RestrictedApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import net.datafaker.providers.base.PersonIdNumber.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.gender;

/**
 * The Norwegian Individual ID Number is 11 digits log
 * Format: ddMMyyCCGkk
 * <ul>
 *     <li>dd - day of birth</li>
 *     <li>MM - month of birth</li>
 *     <li>yy - year of birth</li>
 *     <li>CC - sequence (range depends on century)</li>
 *     <li>G  - a random digit (even for females, odd for males)</li>
 *     <li>kk - checksum</li>
 * </ul>
 * <p>
 *     Example: 11077941012
 * </p>
 *
 * @see <a href="https://www.skatteetaten.no/en/person/national-registry/identitetsnummer-og-elektronisk-id/fodselsnummer/">Overview</a>
 * @see <a href="https://taxid.pro/">Online generator</a>
 * @see <a href="https://www.oecd.org/content/dam/oecd/en/topics/policy-issue-focus/aeoi/norway-tin.pdf">More on centuries</a>
 */
public class NorwegianIdNumber implements IdNumberGenerator {
    private static final DateTimeFormatter BIRTHDAY_FORMAT = DateTimeFormatter.ofPattern("ddMMyy");

    private static final int[] CHECKSUM_COEFFICIENTS_K1 = {3, 7, 6, 1, 8, 9, 4, 5, 2};
    private static final int[] CHECKSUM_COEFFICIENTS_K2 = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};

    @Override
    public String countryCode() {
        return "NO";
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        LocalDate birthday = birthday(faker, request);
        Gender gender = gender(faker, request);

        String basePart = basePart(faker, birthday, gender);
        String idNumber = "%s%02d".formatted(basePart, checksum(basePart));
        return new PersonIdNumber(idNumber, birthday, gender);
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        String valid = generateValid(faker);
        String basePart = valid.substring(0, valid.length() - 2);
        int invalidChecksum = (checksum(basePart) + 1) % 100;
        return "%s%02d".formatted(basePart, invalidChecksum);
    }

    @RestrictedApi(
        explanation = "Not intended for end users",
        allowedOnPath = ".*NorwegianIdNumber\\.java|.*NorwegianIdNumberTest\\.java"
    )
    String basePart(BaseProviders faker, LocalDate birthday, Gender gender) {
        String birthdayDigits = BIRTHDAY_FORMAT.format(birthday);
        int sequenceNumber = generateSequenceNumber(faker, birthday.getYear());
        int genderDigit = genderDigit(faker, gender);

        return "%s%02d%s".formatted(birthdayDigits, sequenceNumber, genderDigit);
    }

    /**
     * <ul>
     * <li>born 1854-1899: allocated from series 749-500</li>
     * <li>born 1900-1999: allocated from series 499-000</li>
     * <li>born 1940-1999: also allocated from series 999-900</li>
     * <li>born 2000-2039: allocated from series 999-500</li>
     * </ul>
     *
     * @see <a href="https://www.oecd.org/content/dam/oecd/en/topics/policy-issue-focus/aeoi/norway-tin.pdf">TIN Structure</a>
     */
    private int generateSequenceNumber(BaseProviders faker, int year) {
        if (year >= 1854 && year <= 1899) {
            return faker.random().nextInt(50, 74);
        } else if (year >= 1940 && year <= 1999) {
            return faker.random().nextInt(90, 99);
        } else if (year >= 1900 && year <= 1999) {
            return faker.random().nextInt(0, 49);
        } else if (year >= 2000 && year <= 2039) {
            return faker.random().nextInt(50, 99);
        } else {
            throw new IllegalArgumentException("Birthday is not in range of supported in Norway");
        }
    }

    private int genderDigit(BaseProviders faker, Gender gender) {
        return switch (gender) {
            case FEMALE -> faker.options().option(0, 2, 4, 6, 8);
            case MALE -> faker.options().option(1, 3, 5, 7, 9);
        };
    }

    /**
     * k1 = 11 - ((3 × d1 + 7 × d2 + 6 × m1 + 1 × m2 + 8 × å1 + 9 × å2 + 4 × i1 + 5 × i2 + 2 × i3) mod 11),
     * k2 = 11 - ((5 × d1 + 4 × d2 + 3 × m1 + 2 × m2 + 7 × å1 + 6 × å2 + 5 × i1 + 4 × i2 + 3 × i3 + 2 × k1) mod 11).
     */
    @RestrictedApi(
        explanation = "Not intended for end users",
        allowedOnPath = ".*NorwegianIdNumber\\.java|.*NorwegianIdNumberTest\\.java"
    )
    int checksum(String numbers) {
        int k1 = modulo11(numbers, CHECKSUM_COEFFICIENTS_K1);
        int k2 = modulo11(numbers + k1, CHECKSUM_COEFFICIENTS_K2);
        return k1 * 10 + k2;
    }

    private int modulo11(String numbers, int[] checksumCoefficients) {
        int checkSum = 0;
        for (int i = 0; i < numbers.length(); i++) {
            int digit = Character.getNumericValue(numbers.charAt(i));
            checkSum += checksumCoefficients[i] * digit;
        }

        return (11 - checkSum % 11) % 10;
    }
}
