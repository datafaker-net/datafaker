package net.datafaker.passportnumbers;

import net.datafaker.Faker;

public class ChPassportNumber {
    private static final String[] validCHPatterns = {
        "E[0-9][0-9]{7}",
        "G[0-9]{8}"};

    /**
     * Generates a valid Chinese passport number
     *
     * @param faker object faker
     * @return a valid Chinese passport number
     */
    public String getValidCh(Faker faker) {
        String ch = faker.regexify("[A-Z][0-9A-Z][0-9]{7}");

        boolean isValid = false;
        for (String validCHPattern : validCHPatterns) {
            if (ch.matches(validCHPattern)) {
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
        for (String validCHPattern : validCHPatterns) {
            if (ch.matches(validCHPattern)) {
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
