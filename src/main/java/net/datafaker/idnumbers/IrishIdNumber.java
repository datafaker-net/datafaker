package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber;
import net.datafaker.providers.base.PersonIdNumber;

import java.util.Random;
import java.util.regex.Pattern;

import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.gender;

/**
 * Generator for Irish Personal Public Service Numbers (PPSN).
 * <p>
 * The PPSN is a unique identifier used in Ireland for citizens and residents.
 * This generator produces both valid and invalid PPSN codes according to official specifications:
 * <ul>
 *   <li>7 numeric digits followed by one or two uppercase letters</li>
 *   <li>The check character is calculated using a Modulo 23 algorithm</li>
 *   <li>An optional suffix (A, B, H, W) may be added and affects the checksum calculation</li>
 * </ul>
 * <b>Reference:</b>
 * <a href="https://www.citizensinformation.ie/en/social-welfare/irish-social-welfare-system/personal-public-service-number/#958a6b">PPSN code</a>
 *
 * <p>Main methods:</p>
 * <ul>
 *   <li>{@link #countryCode()} Returns the ISO-2 country code ("IE")</li>
 *   <li>{@link #generateValid(BaseProviders)} Generates a valid PPSN as a string</li>
 *   <li>{@link #generateInvalid(BaseProviders)} Generates an invalid PPSN</li>
 *   <li>{@link #generateValid(BaseProviders, IdNumber.IdNumberRequest)} Generates a valid {@link PersonIdNumber} object</li>
 *   <li>{@link #validateAndCheckModulo23(String)} Validates a PPSN and checks its checksum</li>
 * </ul>
 */
public class IrishIdNumber implements IdNumberGenerator {

    private static final Pattern IRISH_PPSN = Pattern.compile("\\d{7}[A-Z]{1,2}$");

    @Override
    public String countryCode() {
        return "IE";
    }

    @Override
    public String generateInvalid(final BaseProviders faker) {
        // Generate 7 digits and Append always invalid character (es: 'Z')
        return faker.number().digits(7) + "Z";
    }

    @Override
    public String generateValid(final BaseProviders faker) {
        int[] weights = {8, 7, 6, 5, 4, 3, 2};
        // Generate 7 digits
        String digitsPpsn = faker.number().digits(7);
        int[] digits = digitsPpsn.chars().map(c -> Character.getNumericValue(c)).toArray();

        String suffix = faker.bool().bool() ? faker.options().option(new String[]{"A", "B", "H", "W"}) : "";
        int sum = 0;

        for (int i = 0; i < 7; i++) {
            sum += digits[i] * weights[i];
        }

        // If we have suffix include it in the checksum
        if (!suffix.isEmpty()) {
            char extraChar = suffix.charAt(0);
            int extraValue = switch (extraChar) {
                case 'A', 'B', 'H' -> extraChar - 'A' + 1;
                case 'W' -> 0;
                default -> 0;
            };
            sum += extraValue * 9;
        }

        // Calculate checksum character for modulus 23
        int remainder = sum % 23;
        char checkChar = (remainder == 0) ? 'W' : (char) ('A' + remainder - 1);

        // Build the PPSN
        return digitsPpsn + checkChar + suffix;
    }

    boolean validateAndCheckModulo23(String ppsn) {
       if (ppsn == null || !IRISH_PPSN.matcher(ppsn).matches()) {
           return false;
       }
        int sum = 0;
        int[] weights = {8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 7; i++) {
            char c = ppsn.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
            sum += Character.getNumericValue(c) * weights[i];
        }
        // if there is a suffix incude it in the checksum
        if (ppsn.length() == 9) {
            char extraChar = ppsn.charAt(8);
            int extraValue = switch (extraChar) {
                case 'A', 'B', 'H' -> extraChar - 'A' + 1;
                case 'W' -> 0;
                default -> -1; // Invalid suffix
            };
            if (extraValue == -1) return false;
            sum += extraValue * 9;
        }
        int remainder = sum % 23;
        return ppsn.charAt(7) == ((remainder == 0) ? 'W' : (char) ('A' + remainder - 1));
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumber.IdNumberRequest request) {
        return new PersonIdNumber(generateValid(faker), birthday(faker, request), gender(faker, request));
    }
}

