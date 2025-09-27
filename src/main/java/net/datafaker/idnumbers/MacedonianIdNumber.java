package net.datafaker.idnumbers;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import net.datafaker.providers.base.PersonIdNumber.Gender;

import java.time.LocalDate;
import java.util.List;

import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.digitAt;
import static net.datafaker.idnumbers.Utils.gender;
import static net.datafaker.idnumbers.Utils.randomGender;

/**
 * The Macedonian Identity Number is a unique personal identification number of 13 digits in a form "DD MM YYY RR BBB K"
 *
 * <a href="https://en.wikipedia.org/wiki/National_identification_number#North_Macedonia">Intro</a>
 * <a href="https://en.wikipedia.org/wiki/Unique_Master_Citizen_Number">Specification</a>
 */
@InternalApi
public class MacedonianIdNumber implements IdNumberGenerator {
    private static final List<String> REGIONS = List.of("41", "42", "43", "44", "45", "46", "47", "48", "49");

    @Override
    public String countryCode() {
        return "MK";
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        LocalDate birthday = birthday(faker, request);
        Gender gender = gender(faker, request);
        String basePart = basePart(faker, birthday, gender);
        return new PersonIdNumber(basePart + checksum(basePart), birthday, gender);
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        LocalDate birthday = faker.timeAndDate().birthday();
        Gender gender = randomGender(faker);
        String basePart = basePart(faker, birthday, gender);
        return basePart + (checksum(basePart) + 1) % 10;
    }

    private String basePart(BaseProviders faker, LocalDate bd, Gender gender) {
        return dd(bd) + mm(bd) + yyy(bd) + rr(faker) + sss(faker, gender);
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
    private String sss(BaseProviders faker, Gender gender) {
        int ordinal = switch (gender) {
            case FEMALE -> faker.number().numberBetween(500, 1000);
            case MALE -> faker.number().numberBetween(0, 500);
        };
        return "%03d".formatted(ordinal);
    }

    @InternalApi
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
}
