package net.datafaker.idnumbers;

import java.time.LocalDate;
import java.util.Optional;

import net.datafaker.Faker;

/**
 * Implementation based on the definition at
 * <a href="https://isap.sejm.gov.pl/isap.nsf/DocDetails.xsp?id=WDU20210000510">https://isap.sejm.gov.pl/isap.nsf/DocDetails.xsp?id=WDU20210000510</a> and the
 * description at <a href="https://en.wikipedia.org/wiki/PESEL">https://en.wikipedia.org/wiki/PESEL</a>
 */
public class PeselNumber {

    public static final int PESEL_LENGTH = 11;

    private final Faker faker;

    public enum Gender {
        MALE, FEMALE, ANY
    }

    public PeselNumber(Faker faker) {
        super();
        this.faker = faker;
    }

    public String get(LocalDate birthDate, Gender gender) {
        return getInternal(birthDate, Optional.ofNullable(gender).orElse(Gender.ANY));
    }

    private String getInternal(LocalDate birthDate, Gender gender) {
        final int[] digits = new int[PESEL_LENGTH - 1];
        digits[0] = birthDate.getYear() / 10 % 10;
        digits[1] = birthDate.getYear() % 10;

        int monthEncoded = getMonthEncoded(birthDate.getYear(), birthDate.getMonthValue());
        digits[2] = monthEncoded / 10;
        digits[3] = monthEncoded % 10;

        digits[4] = birthDate.getDayOfMonth() / 10;
        digits[5] = birthDate.getDayOfMonth() % 10;

        digits[6] = randomDigit();
        digits[7] = randomDigit();
        digits[8] = randomDigit();

        digits[9] = getGenderDigit(gender);

        final int controlDigit = getControlDigit(digits);

        final StringBuilder peselSb = new StringBuilder(PESEL_LENGTH);
        for (int digit : digits) {
            peselSb.append(digit);
        }
        peselSb.append(controlDigit);
        return peselSb.toString();
    }

    private int randomDigit() {
        return faker.random().nextInt(10);
    }

    private int getControlDigit(int[] digits) {
        final int sum = digits[0] + digits[4] + digits[8] + (digits[1] + digits[5] + digits[9]) * 3
            + (digits[2] + digits[6]) * 7 + (digits[3] + digits[7]) * 9;
        return (10 - sum % 10) % 10;
    }

    private int getGenderDigit(Gender gender) {
        switch (gender) {
            case FEMALE:
                return faker.random().nextInt(5) * 2;
            case MALE:
                return faker.random().nextInt(5) * 2 + 1;
            default:
                return randomDigit();
        }
    }

    private int getMonthEncoded(int year, int month) {
        final int monthModifier;
        if (year < 1800) {
            throw new IllegalArgumentException("Year is before 1800");
        } else if (year < 1900) {
            monthModifier = 80;
        } else if (year < 2000) {
            monthModifier = 0;
        } else if (year < 2100) {
            monthModifier = 20;
        } else if (year < 2200) {
            monthModifier = 40;
        } else if (year < 2300) {
            monthModifier = 60;
        } else {
            throw new IllegalArgumentException("Year is after 2300");
        }
        return month + monthModifier;
    }
}
