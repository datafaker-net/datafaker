package net.datafaker.passportnumbers;

import net.datafaker.Faker;

public class AmPassportNumber {
    private static final String[] validAMPatterns = {"[0-9]{8}"};

    /**
     * Generates a valid American passport number
     *
     * @param faker object faker
     * @return a valid American passport number
     */
    public String getValidAm(Faker faker) {
        String am = faker.regexify("[0-9A-Z]{8}");

        boolean isValid = false;
        for (String validAMPattern : validAMPatterns) {
            if (am.matches(validAMPattern)) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            am = getValidAm(faker);
        }
        return am;
    }

    /**
     * Generates an invalid American passport number
     *
     * @param faker object faker
     * @return an invalid American passport number
     */
    public String getInvalidAm(Faker faker) {
        String am = faker.regexify("[A-Z0-9]{1,}");

        boolean isValid = true;
        for (String validAMPattern : validAMPatterns) {
            if (am.matches(validAMPattern)) {
                continue;
            }
            isValid = false;
        }
        if (isValid) {
            am = getInvalidAm(faker);
        }
        return am;
    }
}
