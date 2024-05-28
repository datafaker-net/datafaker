package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;

import java.time.LocalDate;
import java.util.List;

/**
 * The Macedonian Identity Number is a unique personal identification number of 13 digits in a form "DD MM YYY RR BBB K"
 *
 * <a href="https://en.wikipedia.org/wiki/National_identification_number#North_Macedonia">Intro</a>
 * <a href="https://en.wikipedia.org/wiki/Unique_Master_Citizen_Number">Specification</a>
 */
public class MacedonianIdNumber implements IdNumbers {
    private static final List<String> REGIONS = List.of("41", "42", "43", "44", "45", "46", "47", "48", "49");

    @Override
    public String country() {
        return "MK";
    }

    public String generateValid(BaseProviders faker) {
        String basePart = basePart(faker);
        return basePart + checksum(basePart);
    }

    public String generateInvalid(BaseProviders faker) {
        String basePart = basePart(faker);
        return basePart + (checksum(basePart) + 1) % 10;
    }

    private String basePart(BaseProviders faker) {
        LocalDate bd = faker.timeAndDate().birthday(0, 120);
        boolean female = faker.bool().bool();
        return dd(bd) + mm(bd) + yyy(bd) + rr(faker) + sss(faker, female);
    }

    private String dd(LocalDate bd) {
        return "%02d".formatted(bd.getDayOfMonth());
    }

    private String mm(LocalDate bd) {
        return "%02d".formatted(bd.getMonthValue());
    }

    /**
     * last three digits of the year of birth
     */
    private String yyy(LocalDate bd) {
        return "%03d".formatted(bd.getYear() % 1000);
    }

    /**
     * The two digit registry number depends on the citizens place of birth
     */
    private String rr(BaseProviders faker) {
        return REGIONS.get(faker.number().numberBetween(0, REGIONS.size()));
    }

    /**
     * The combination of the citizen's sex and ordinal number of birth is presented as a 3-digit number
     * - from 000 to 499 for the male, and
     * - from 500 to 999 for the female citizens.
     */
    private String sss(BaseProviders faker, boolean female) {
        int ordinal = female ? faker.number().numberBetween(500, 1000) : faker.number().numberBetween(0, 500);
        return "%03d".formatted(ordinal);
    }

    int checksum(String text) {
        int a = digitAt(text, 0);
        int b = digitAt(text, 1);
        int c = digitAt(text, 2);
        int d = digitAt(text, 3);
        int e = digitAt(text, 4);
        int f = digitAt(text, 5);
        int g = digitAt(text, 6);
        int h = digitAt(text, 7);
        int i = digitAt(text, 8);
        int j = digitAt(text, 9);
        int k = digitAt(text, 10);
        int l = digitAt(text, 11);

        int m = 11 - (7 * (a + g) + 6 * (b + h) + 5 * (c + i) + 4 * (d + j) + 3 * (e + k) + 2 * (f + l)) % 11;

        // If m is between 1 and 9, the checksum is the same as the number m;
        // If m is 10 or 11 checksum becomes 0 (zero).
        return switch (m) {
            case 10, 11 -> 0;
            default -> m;
        };
    }

    private int digitAt(String text, int index) {
        return text.charAt(index) - '0';
    }
}
