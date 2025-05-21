package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber;
import net.datafaker.providers.base.PersonIdNumber;

import java.util.Random;

/**
 * Generates ID numbers for Irish citizens and Residents
 * <p>
 * See <a href="https://www.citizensinformation.ie/en/social-welfare/irish-social-welfare-system/personal-public-service-number/#958a6b">PPSN code</a>.
 */
public class IrishIdNumber implements IdNumberGenerator {
    @Override
    public String countryCode() {
        return "IE";
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        return "1234567F";
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumber.IdNumberRequest request) {
        Random random = new Random();
        int[] weights = {8, 7, 6, 5, 4, 3, 2};
        int[] digits = new int[7];
        String[] allowedSuffixes = {"A", "B", "H", "W"};
        boolean addSuffix = random.nextBoolean(); // 50% chance
        String suffix = addSuffix ? allowedSuffixes[random.nextInt(allowedSuffixes.length)] : "";
        int sum = 0;

        // Generate 7 digits
        for (int i = 0; i < 7; i++) {
            digits[i] = random.nextInt(10); // 0–9
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
        StringBuilder ppsn = new StringBuilder();
        for (int d : digits) {
            ppsn.append(d);
        }
        ppsn.append(checkChar).append(suffix);

        return new PersonIdNumber(ppsn.toString(), null, null);
    }

    public boolean checkModulo23(String ppsn) {
        if (ppsn == null || (ppsn.length() != 8 && ppsn.length() != 9)) {
            return false;
        }
        int sum = 0;
        int[] weights = {8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 7; i++) {
            char c = ppsn.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
            sum += (c - '0') * weights[i];
        }
        // if there is a suffic incude it in the checksum
        if (ppsn.length() == 9) {
            char extraChar = ppsn.charAt(8);
            int extraValue = switch (extraChar) {
                case 'A', 'B', 'H' -> extraChar - 'A' + 1;
                case 'W' -> 0;
                default -> -1; // Suffisso non valido
            };
            if (extraValue == -1) return false;
            sum += extraValue * 9;
        }
        char checkChar = ppsn.charAt(7);
        int remainder = sum % 23;
        char expectedCheckChar = (remainder == 0) ? 'W' : (char) ('A' + remainder - 1);
        return checkChar == expectedCheckChar;
    }
}

