package com.github.javafaker.passportnumbers;

import com.github.javafaker.Faker;

public class AmPassportNumber {
    private static final String[] validCHPatterns = {"[0-9]{8}"};

    /**
     * Generates a valid America passport number
     *
     * @param faker object faker
     * @return a valid Chinese passport number
     */
    public String getValidAm(Faker faker) {
        String ch = faker.regexify("[0-9A-Z]{8}");

        boolean isValid = false;
        for (String validCHPattern : validCHPatterns) {
            if (ch.matches(validCHPattern)) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            ch = getValidAm(faker);
        }
        return ch;
    }

    /**
     * Generates a invalid America passport number
     *
     * @param faker object faker
     * @return a invalid Chinese passport number
     */
    public String getInvalidAm(Faker faker) {
        String ch = faker.regexify("[A-Z0-9]{1,}");

        boolean isValid = true;
        for (String validCHPattern : validCHPatterns) {
            if (ch.matches(validCHPattern)) {
                continue;
            }
            isValid = false;
        }
        if (isValid) {
            ch = getInvalidAm(faker);
        }
        return ch;
    }
}
