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
import static net.datafaker.idnumbers.Utils.multiply;
import static net.datafaker.idnumbers.Utils.randomGender;

/**
 * The Romanian Cod Numeric Personal (CNP), or Personal Numeric Code
 * is a unique identifying number consisting of 13 digits.
 *
 * <a href="https://en.wikipedia.org/wiki/Romanian_identity_card#CNP">Description</a>
 */
@InternalApi
public class RomanianIdNumber implements IdNumberGenerator {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    private static final int[] CHECKSUM_WEIGHTS = {2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9};

    @Override
    public String countryCode() {
        return "RO";
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        LocalDate birthday = birthday(faker, request);
        Gender gender = gender(faker, request);
        String basePart = basePart(faker, birthday, gender);
        String idNumber = basePart + checksum(basePart);
        return new PersonIdNumber(idNumber, birthday, gender);
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        LocalDate birthday = faker.timeAndDate().birthday();
        Gender gender = randomGender(faker);
        String basePart = basePart(faker, birthday, gender);
        return basePart + (checksum(basePart) + 1) % 10;
    }

    private String basePart(BaseProviders faker, LocalDate birthday, Gender gender) {
        return firstCharacter(birthday, gender) +
            dateOfBirth(birthday) + countyCode(faker) + sequenceNumber(faker);
    }

    /**
     * Represents the gender and century in which the person was born and can be:
     * – 1 for male persons born between 1900-1999;
     * – 2 for female persons born between 1900-1999;
     * – 3 for male persons born between 1800-1899;
     * – 4 for female persons born between 1800-1899;
     * – 5 for male persons born between 2000-2099;
     * – 6 for female persons born between the years 2000-2099;
     */
    @InternalApi
    int firstCharacter(LocalDate birthday, Gender gender) {
        int digit = switch (birthday.getYear() / 100) {
            case 18 -> 3;
            case 19 -> 1;
            case 20 -> 5;
            default -> throw new IllegalArgumentException("Too far in the past or future: " + birthday);
        };

        return switch (gender) {
            case FEMALE -> digit + 1;
            case MALE -> digit;
        };
    }

    @InternalApi
    String dateOfBirth(LocalDate birthday) {
        return DATE_TIME_FORMATTER.format(birthday);
    }

    /**
     * Character 8–9: 01–46 or 51 or 52
     */
    @InternalApi
    String countyCode(BaseProviders faker) {
        int countyCode = faker.bool().bool() ?
            faker.number().numberBetween(1, 47) :
            faker.number().numberBetween(51, 53);
        return "%02d".formatted(countyCode);
    }

    /**
     * next 3 digits is a number between 001 and 999.
     * Each number is allocated only once per person per day.
     */
    @InternalApi
    String sequenceNumber(BaseProviders faker) {
        return "%03d".formatted(faker.number().numberBetween(1, 1_000));
    }

    /**
     * last digit is a control digit calculated from all the other 12 digits in the code as follows:
     * (n1*2+n2*7+n3*9+n4*1+n5*4+n6*6+n7*3+n8*5+n9*8+n10*2+n11*7+n12*9)%11
     *
     * if the result is 10 then the digit is 1, otherwise is the result.
     */
    @InternalApi
    int checksum(String basePart) {
        int result = multiply(basePart, CHECKSUM_WEIGHTS) % 11;
        return result == 10 ? 1 : result;
    }
}
