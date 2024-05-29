package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;

import java.time.LocalDate;

/**
 * <a href="https://en.wikipedia.org/wiki/Unique_citizenship_number">Specification</a>
 */
public class BulgarianIdNumber implements IdNumbers {
    private static final int[] CHECKSUM_WEIGHTS = {2, 4, 8, 5, 10, 9, 7, 3, 6};
    private static final int[] EVEN_DIGITS = {0, 2, 4, 6, 8};
    private static final int[] ODD_DIGITS = {1, 3, 5, 7, 9};

    @Override
    public String countryCode() {
        return "BG";
    }

    @Override
    public String generateValid(BaseProviders faker) {
        String basePart = basePart(faker);
        return basePart + checksum(basePart);
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        String basePart = basePart(faker);
        return basePart + (checksum(basePart) + 1) % 10;
    }

    private String basePart(BaseProviders faker) {
        LocalDate birthDate = faker.timeAndDate().birthday(0, 200);
        boolean female = faker.bool().bool();
        return yy(birthDate) + mm(birthDate) + dd(birthDate) + order(faker, female);
    }

    private String yy(LocalDate birthDate) {
        return "%02d".formatted(birthDate.getYear() % 100);
    }

    private String mm(LocalDate birthDate) {
        int monthAddition = birthDate.getYear() < 1900 ? 20  :
            birthDate.getYear() >= 2000 ? 40 : 0;
        return "%02d".formatted(birthDate.getMonthValue() + monthAddition);
    }

    private String dd(LocalDate birthDate) {
        return "%02d".formatted(birthDate.getDayOfMonth());
    }

    private String order(BaseProviders faker, boolean female) {
        int[] availableLastDigits = female ? ODD_DIGITS : EVEN_DIGITS;
        int lastDigit = availableLastDigits[faker.number().numberBetween(0, 5)];
        return faker.number().digits(2) + lastDigit;
    }

    int checksum(String text) {
        int checksum = 0;
        for (int i = 0; i < text.length(); i++) {
            checksum += digitAt(text, i) * CHECKSUM_WEIGHTS[i];
        }
        return (checksum % 11) % 10;
    }

    private int digitAt(String text, int index) {
        return text.charAt(index) - '0';
    }
}
