package net.datafaker.idnumbers;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber;
import net.datafaker.providers.base.PersonIdNumber;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.Character.getNumericValue;
import static net.datafaker.idnumbers.Utils.randomGender;

/**
 * Hungarian personal identification number
 * <p>
 * The structure of such number is GYYMMDDXXC, whereas
 * <ul>
 *  <li>G indicates century of birth and gender,</li>
 *  <li>YYMMDD indicates birth year, month and day,</li>
 *  <li>XX is the serial number, and</li>
 *  <li>C is a checksum digit.</li>
 * </ul>
 * <p>
 * See <a href="https://en.wikipedia.org/wiki/National_identification_number#Hungary">Hungarian identification number</a>
 */
@InternalApi
public class HungarianIdNumber implements IdNumberGenerator {

    private static final DateTimeFormatter BIRTHDAY_FORMAT = DateTimeFormatter.ofPattern("yyMMdd");

    @Override
    public String countryCode() {
        return "HU";
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        String validIdNumber = generateValid(faker);
        String basePart = validIdNumber.substring(0, 9);
        int validCheckDigit = getNumericValue(validIdNumber.charAt(9));
        int invalidCheckDigit = (validCheckDigit + 1) % 10;
        return basePart + invalidCheckDigit;
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumber.IdNumberRequest request) {
        LocalDate birthday = faker.timeAndDate().birthday();
        PersonIdNumber.Gender gender = randomGender(faker);
        int checkDigit;
        String basePart;
        do {
            basePart = basePart(faker, birthday, gender);
            checkDigit = getCheckDigit(basePart);
        } while (checkDigit == 10);

        String idNumber = basePart + checkDigit;
        return new PersonIdNumber(idNumber, birthday, gender);
    }

    private String basePart(BaseProviders faker, LocalDate birthday, PersonIdNumber.Gender gender) {
        return firstDigit(birthday.getYear(), gender) +
            BIRTHDAY_FORMAT.format(birthday) +
            faker.number().digits(2);
    }

    @InternalApi
    static int getCheckDigit(String basePart) {
        char[] numbers = basePart.toCharArray();
        int summ = 0;
        for (int i = 0; i < numbers.length; i++) {
            summ += getNumericValue(numbers[i]) * (i + 1);
        }

        return summ % 11;
    }

    @InternalApi
    static int firstDigit(int birthYear, PersonIdNumber.Gender gender) {
        return switch (gender) {
            case MALE -> isInRange(birthYear) ? 1 : 3;
            case FEMALE -> isInRange(birthYear) ? 2 : 4;
        };
    }

    private static boolean isInRange(int birthYear) {
        return birthYear >= 1900 && birthYear <= 1999;
    }

}
