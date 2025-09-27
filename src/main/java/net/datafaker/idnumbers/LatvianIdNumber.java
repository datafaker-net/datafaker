package net.datafaker.idnumbers;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import net.datafaker.providers.base.PersonIdNumber.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static net.datafaker.idnumbers.Utils.*;

/**
 * Latvian personal identification number
 * See <a href="https://en.wikipedia.org/wiki/National_identification_number#Latvia">Latvian identification number</a>
 */
@InternalApi
public class LatvianIdNumber implements IdNumberGenerator {
    private static final DateTimeFormatter BIRTHDAY_FORMAT = DateTimeFormatter.ofPattern("ddMMyy");
    private static final int[] CHECKSUM_COEFFICIENTS = {1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final LocalDate CHANGE_TO_NEW_FORMAT_DATE = LocalDate.of(2017, 7, 1);

    @Override
    public String countryCode() {
        return "LV";
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        LocalDate birthday = birthday(faker, request);
        Gender gender = gender(faker, request);

        if (birthday.isBefore(CHANGE_TO_NEW_FORMAT_DATE)) {
            String digits = basePart(faker, birthday);
            String idNumber = digits + checksum(digits);
            return new PersonIdNumber(idNumber, birthday, gender);
        } else {
            String idNumber = "3" + faker.number().numberBetween(2000000000L, 9999999999L);
            return new PersonIdNumber(idNumber, birthday, gender);
        }
    }

    @Override
    public String generateInvalid(final BaseProviders faker) {
        LocalDate birthday = faker.timeAndDate().birthday();
        String digits = basePart(faker, birthday);
        return digits + (checksum(digits) + 1) % 10;
    }

    private String basePart(BaseProviders faker, LocalDate birthday) {
        return BIRTHDAY_FORMAT.format(birthday) +
            "-" +
            centuryDigit(birthday.getYear()) +
            faker.number().digits(3);
    }

    @InternalApi
    static int centuryDigit(int birthYear) {
        return switch (birthYear / 100) {
            case 18 -> 0;
            case 19 -> 1;
            case 20 -> 2;
            default -> throw new IllegalStateException("Birth year %s is out of allowed range [1800, 2017]".formatted(birthYear));
        };
    }

    @InternalApi
    static int checksum(String numbers) {
        int checkSum = 0;
        numbers = numbers.replace("-", "");

        for (int i = 0; i < numbers.length(); i++) {
            int digit = Character.getNumericValue(numbers.charAt(i));
            checkSum += CHECKSUM_COEFFICIENTS[i] * digit;
        }

        return ((1101 - checkSum) % 11) % 10;
    }
}
