package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import net.datafaker.providers.base.PersonIdNumber.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.gender;

/**
 * The Norwegian Individual ID Number is 11 digits log
 * Format: ddMMyyCCGkk
 * <ul>
 *     <li>dd - day of birth</li>
 *     <li>MM - month of birth</li>
 *     <li>yy - year of birth</li>
 *     <li>CC - sequence (range depends on century)</li>
 *     <li>G  - a random digit (even for females, odd for males)</li>
 *     <li>kk - checksum</li>
 * </ul>
 * <p>
 *     Example: 11077941012
 * </p>
 * @see <a href="https://www.skatteetaten.no/en/person/national-registry/identitetsnummer-og-elektronisk-id/fodselsnummer/">Overview</a>
 * @see <a href="https://taxid.pro/">Online generator</a>
 * @see <a href="https://www.oecd.org/content/dam/oecd/en/topics/policy-issue-focus/aeoi/norway-tin.pdf">More on centuries</a>
 */
public class NorwegianIdNumber implements IdNumberGenerator {
    private static final DateTimeFormatter BIRTHDAY_FORMAT = DateTimeFormatter.ofPattern("ddMMyy");
    private static final List<Map.Entry<Interval, Interval>> CENTURIES = Stream.of(
            "1854-1899, 74-50",
            "1940-1999, 99-90",
            "1900-1999, 49-0",
            "2000-2039, 99-50"
        )
        .map(s -> s.split(", "))
        .map(array -> Map.entry(Interval.of(array[0]), Interval.of(array[1]).reverse()))
        .toList();

    private static final int[] CHECKSUM_COEFFICIENTS_K1 = {3, 7, 6, 1, 8, 9, 4, 5, 2};
    private static final int[] CHECKSUM_COEFFICIENTS_K2 = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};

    @Override
    public String countryCode() {
        return "NO";
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        LocalDate birthday = birthday(faker, request);
        Gender gender = gender(faker, request);

        String basePart = basePart(faker, birthday, gender);
        String idNumber = "%s%02d".formatted(basePart, checksum(basePart));
        return new PersonIdNumber(idNumber, birthday, gender);
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        String valid = generateValid(faker);
        String basePart = valid.substring(0, valid.length() - 2);
        int invalidChecksum = (checksum(basePart) + 1) % 100;
        return "%s%02d".formatted(basePart, invalidChecksum);
    }

    String basePart(BaseProviders faker, LocalDate birthday, Gender gender) {
        String birthdayDigits = BIRTHDAY_FORMAT.format(birthday);

        int sequenceNumber = CENTURIES
            .stream()
            .filter(e -> e.getKey().contains(birthday.getYear()))
            .map(Map.Entry::getValue)
            .map(interval -> faker.random().nextInt(interval.from, interval.to))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Birthday is not in range of supported in Norway"));

        int genderDigit = gender == Gender.FEMALE
            ? faker.options().option(0, 2, 4, 6, 8)
            : faker.options().option(1, 3, 5, 7, 9);

        return "%s%02d%s".formatted(birthdayDigits, sequenceNumber, genderDigit);
    }

    /**
     * k1 = 11 - ((3 × d1 + 7 × d2 + 6 × m1 + 1 × m2 + 8 × å1 + 9 × å2 + 4 × i1 + 5 × i2 + 2 × i3) mod 11),
     * k2 = 11 - ((5 × d1 + 4 × d2 + 3 × m1 + 2 × m2 + 7 × å1 + 6 × å2 + 5 × i1 + 4 × i2 + 3 × i3 + 2 × k1) mod 11).
     */
    int checksum(String numbers) {
        int k1 = modulo11(numbers, CHECKSUM_COEFFICIENTS_K1);
        int k2 = modulo11(numbers + k1, CHECKSUM_COEFFICIENTS_K2);
        return k1 * 10 + k2;
    }

    private int modulo11(String numbers, int[] checksumCoefficients) {
        int checkSum = 0;
        for (int i = 0; i < numbers.length(); i++) {
            int digit = Character.getNumericValue(numbers.charAt(i));
            checkSum += checksumCoefficients[i] * digit;
        }

        return (11 - checkSum % 11) % 10;
    }

    private record Interval(int from, int to) {
        public static Interval of(String string) {
            String[] split = string.split("-");
            return new Interval(parseInt(split[0]), parseInt(split[1]));
        }

        public boolean contains(int value) {
            return value >= from && value <= to;
        }

        public Interval reverse() {
            return new Interval(to, from);
        }
    }
}
