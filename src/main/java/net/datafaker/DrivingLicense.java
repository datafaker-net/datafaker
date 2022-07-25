package net.datafaker;

import java.util.Locale;

/**
 * @since 1.5.0
 */
public class DrivingLicense {

    private final Faker faker;

    protected DrivingLicense(Faker faker) {
        this.faker = faker;
    }

    public String drivingLicense(String stateAbbreviation) {
        return faker.regexify(faker.bothify(faker.resolve("driving_license.usa." + stateAbbreviation))).toUpperCase(Locale.ROOT);
    }
}
