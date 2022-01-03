package net.datafaker.passportnumbers;

import net.datafaker.Faker;

public class ChPassportNumber {
    private static final String[] validCHPatterns = {
            "E[0-9][0-9]{7}",
            "G[0-9]{8}"};

    /**
     * Generates a valid Chinese passport number
     *
     * @param f object faker
     * @return a valid Chinese passport number
     */
    public String getValidCh(Faker f) {
        String ch = f.regexify("[A-Z][0-9A-Z][0-9]{7}");

        boolean isValid = false;
        for (String validCHPattern : validCHPatterns) {
            if (ch.matches(validCHPattern)) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            ch = getValidCh(f);
        }
        return ch;
    }

    /**
     * Generates a invalid Chinese passport number
     *
     * @param f
     *            object faker
     * @return a invalid Chinese passport number
     */
    public String getInvalidCh(Faker f) {
        String ch = f.regexify("[A-Z0-9]{1,}");

        boolean isValid = true;
        for (String validCHPattern : validCHPatterns) {
            if (ch.matches(validCHPattern)) {
                continue;
            }
            isValid = false;
        }
        if (isValid) {
            ch = getInvalidCh(f);
        }
        return ch;
    }
}
