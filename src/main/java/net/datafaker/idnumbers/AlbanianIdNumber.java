package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;

import java.time.LocalDate;

/**
 * The Albanian Identity Number is a unique personal identification number of 10 characters in the format YYMMDDSSSC
 */
public class AlbanianIdNumber implements IdNumbers {
    private static final String FIRST_CHAR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String CHECKSUM_CHAR = "WABCDEFGHIJKLMNOPQRSTUV";

    public String getInvalid(BaseProviders faker) {
        String pin = getValid(faker);
        int invalidMonth = faker.number().numberBetween(93, 99);
        return pin.substring(0, 2) + invalidMonth + pin.substring(4);
    }

    public String getValid(BaseProviders faker) {
        LocalDate birthDate = faker.date().birthdayLocalDate(0, 200);
        boolean female = Math.random() >= 0.5;
        String basePart = yy(birthDate.getYear()) + mm(birthDate.getMonthValue(), female) + dd(birthDate.getDayOfMonth()) + sss(faker);
        return basePart + checksum(basePart);
    }

    String yy(int year) {
        return FIRST_CHAR.charAt((year - 1800) / 10) + String.valueOf(year % 10);
    }

    String mm(int month, boolean female) {
        return String.format("%02d", (female ? 50 : 0) + month);
    }

    String dd(int dayOfMonth) {
        return String.format("%02d", dayOfMonth);
    }

    private String sss(BaseProviders faker) {
        return faker.number().digits(3);
    }

    char checksum(String text) {
        int checksum = checksumOfFirstChar(text.charAt(0));
        for (int i = 1; i < text.length(); i++) {
            checksum += digitAt(text, i) * i;
        }
        return CHECKSUM_CHAR.charAt(checksum % 23);
    }

    int checksumOfFirstChar(char c) {
        return Character.isLetter(c) ? CHECKSUM_CHAR.indexOf(c) : digit(c);
    }

    private int digitAt(String text, int index) {
        return digit(text.charAt(index));
    }

    int digit(char c) {
        return c - '0';
    }
}
