package net.datafaker.idnumbers;

import java.time.LocalDate;
import java.util.Optional;
import net.datafaker.Faker;

/**
 * Implementation based on the definition at
 * https://isap.sejm.gov.pl/isap.nsf/DocDetails.xsp?id=WDU20210000510 and the
 * description at https://en.wikipedia.org/wiki/PESEL
 */
public class PeselNumber {

    public static final int PESEL_LENGTH = 11;

    private final Faker faker;

    public enum Gender {
	MALE, FEMALE, ANY;
    }

    public PeselNumber(Faker faker) {
	super();
	this.faker = faker;
    }

    public String get(LocalDate birthDate, Gender gender) {
	return getInternal(birthDate, Optional.ofNullable(gender).orElse(Gender.ANY));
    }

    private String getInternal(LocalDate birthDate, Gender gender) {
	final int yearDigit2 = birthDate.getYear() / 10 % 10;
	final int yearDigit1 = birthDate.getYear() % 10;

	int monthEncoded = getMonthEncoded(birthDate.getYear(), birthDate.getMonthValue());
	final int monthDigit2 = monthEncoded / 10;
	final int monthDigit1 = monthEncoded % 10;

	final int dayDigit2 = birthDate.getDayOfMonth() / 10;
	final int dayDigit1 = birthDate.getDayOfMonth() % 10;

	final int uinDigit3 = randomDigit();
	final int uinDigit2 = randomDigit();
	final int uinDigit1 = randomDigit();

	final int genderDigit = getGenderDigit(faker, gender);

	final int controlDigit = getControlDigit(yearDigit2, yearDigit1, monthDigit2, monthDigit1, dayDigit2, dayDigit1,
		uinDigit3, uinDigit2, uinDigit1, genderDigit);

	final StringBuilder peselSb = new StringBuilder(PESEL_LENGTH);
	peselSb.append(yearDigit2);
	peselSb.append(yearDigit1);
	peselSb.append(monthDigit2);
	peselSb.append(monthDigit1);
	peselSb.append(dayDigit2);
	peselSb.append(dayDigit1);
	peselSb.append(uinDigit3);
	peselSb.append(uinDigit2);
	peselSb.append(uinDigit1);
	peselSb.append(genderDigit);
	peselSb.append(controlDigit);
	return peselSb.toString();
    }

    private int randomDigit() {
	return faker.random().nextInt(10);
    }

    private int getControlDigit(int yearDigit2, int yearDigit1, int monthDigit2, int monthDigit1, int dayDigit2,
	    int dayDigit1, int uinDigit3, int uinDigit2, int uinDigit1, int genderDigit) {
	final int sum = yearDigit2 + dayDigit2 + uinDigit1 + (yearDigit1 + dayDigit1 + genderDigit) * 3
		+ (monthDigit2 + uinDigit3) * 7 + (monthDigit1 + uinDigit2) * 9;
	return (10 - sum % 10) % 10;
    }

    private int getGenderDigit(Faker f, Gender gender) {
	switch (gender) {
	case FEMALE:
	    return f.random().nextInt(5) * 2;
	case MALE:
	    return f.random().nextInt(5) * 2 + 1;
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
