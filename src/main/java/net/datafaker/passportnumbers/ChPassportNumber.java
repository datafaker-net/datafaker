package net.datafaker.passportnumbers;

import net.datafaker.core.Faker;

import java.util.regex.Pattern;

public class ChPassportNumber {
    private static final Pattern[] validCHPatterns = {
        Pattern.compile("E\\d\\d{7}"),
        Pattern.compile("G\\d{8}")};

    /**
     * Generates a valid Chinese passport number
     *
     * @param faker object faker
     * @return a valid Chinese passport number
     */
    public String getValidCh(Faker faker) {
        String ch = faker.regexify("[A-Z][0-9A-Z][0-9]{7}");

        boolean isValid = false;
        for (Pattern validCHPattern : validCHPatterns) {
            if (validCHPattern.matcher(ch).matches()) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            ch = getValidCh(faker);
        }
        return ch;
    }

    /**
     * Generates an invalid Chinese passport number
     *
     * @param faker object faker
     * @return an invalid Chinese passport number
     */
    public String getInvalidCh(Faker faker) {
        String ch = faker.regexify("[A-Z0-9]{1,}");

        boolean isValid = true;
        for (Pattern validCHPattern : validCHPatterns) {
            if (validCHPattern.matcher(ch).matches()) {
                continue;
            }
            isValid = false;
        }
        if (isValid) {
            ch = getInvalidCh(faker);
        }
        return ch;
    }
}
