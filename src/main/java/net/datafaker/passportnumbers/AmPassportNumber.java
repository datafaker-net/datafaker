package net.datafaker.passportnumbers;

import net.datafaker.Faker;

import java.util.regex.Pattern;

public class AmPassportNumber {
    private static final Pattern[] VALID_AM_PATTERNS = {Pattern.compile("\\d{8}")};

    /**
     * Generates a valid American passport number
     *
     * @param faker object faker
     * @return a valid American passport number
     */
    public String getValidAm(Faker faker) {
        String am = faker.regexify("[0-9A-Z]{8}");

        boolean isValid = false;
        for (Pattern validAMPattern : VALID_AM_PATTERNS) {
            if (validAMPattern.matcher(am).matches()) {
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
        for (Pattern validAMPattern : VALID_AM_PATTERNS) {
            if (validAMPattern.matcher(am).matches()) {
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
