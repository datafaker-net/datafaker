package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import net.datafaker.providers.base.PersonIdNumber.Gender;

import java.time.LocalDate;

import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.gender;
import static net.datafaker.idnumbers.Utils.randomGender;

/**
 * Implementation based on the definition at
 * <a href="https://isap.sejm.gov.pl/isap.nsf/DocDetails.xsp?id=WDU20210000510">https://isap.sejm.gov.pl/isap.nsf/DocDetails.xsp?id=WDU20210000510</a> and the
 * description at <a href="https://en.wikipedia.org/wiki/PESEL">https://en.wikipedia.org/wiki/PESEL</a>
 */
public class PolishIdNumber implements IdNumberGenerator {

    public static final int PESEL_LENGTH = 11;

    @Override
    public String countryCode() {
        return "PL";
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        LocalDate birthday = birthday(faker, request);
        Gender gender = gender(faker, request);
        return new PersonIdNumber(get(faker, birthday, gender), birthday, gender);
    }

    String get(BaseProviders faker, LocalDate birthDate, Gender gender) {
        int[] digits = generateDigits(faker, birthDate, gender);
        int controlDigit = getControlDigit(digits);
        return toString(digits, controlDigit);
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        Gender gender = randomGender(faker);
        int[] digits = generateDigits(faker, faker.timeAndDate().birthday(), gender);
        int controlDigit = getControlDigit(digits);
        int invalidControlDigit = (controlDigit + 1) % 10;
        return toString(digits, invalidControlDigit);
    }

    private int[] generateDigits(BaseProviders faker, LocalDate birthDate, Gender gender) {
        int monthEncoded = getMonthEncoded(birthDate.getYear(), birthDate.getMonthValue());
        return new int[]{
            birthDate.getYear() / 10 % 10,
            birthDate.getYear() % 10,

            monthEncoded / 10,
            monthEncoded % 10,

            birthDate.getDayOfMonth() / 10,
            birthDate.getDayOfMonth() % 10,

            randomDigit(faker),
            randomDigit(faker),
            randomDigit(faker),

            getGenderDigit(faker, gender)
        };
    }

    private static String toString(int[] digits, int controlDigit) {
        final StringBuilder peselSb = new StringBuilder(PESEL_LENGTH);
        for (int digit : digits) {
            peselSb.append(digit);
        }
        peselSb.append(controlDigit);
        return peselSb.toString();
    }

    private int randomDigit(BaseProviders faker) {
        return faker.number().randomDigit();
    }

    private int getControlDigit(int[] digits) {
        final int sum = digits[0] + digits[4] + digits[8] + (digits[1] + digits[5] + digits[9]) * 3
            + (digits[2] + digits[6]) * 7 + (digits[3] + digits[7]) * 9;
        return (10 - sum % 10) % 10;
    }

    private int getGenderDigit(BaseProviders faker, Gender gender) {
        return switch (gender) {
            case FEMALE -> faker.random().nextInt(5) * 2;
            case MALE -> faker.random().nextInt(5) * 2 + 1;
        };
    }

    private int getMonthEncoded(int year, int month) {
        return month + monthModifier(year);
    }

    private int monthModifier(int year) {
        if (year < 1800) {
            throw new IllegalArgumentException("Year is before 1800: " + year);
        } else if (year < 1900) {
            return 80;
        } else if (year < 2000) {
            return 0;
        } else if (year < 2100) {
            return 20;
        } else if (year < 2200) {
            return 40;
        } else if (year < 2300) {
            return 60;
        } else {
            throw new IllegalArgumentException("Year is after 2300: " + year);
        }
    }
}
