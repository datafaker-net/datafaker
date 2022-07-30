package net.datafaker;

import java.util.Locale;

/**
 * @since 1.5.0
 */
public class DrivingLicense extends AbstractProvider {

    protected DrivingLicense(Faker faker) {
        super(faker);
    }

    public String drivingLicense(String stateAbbreviation) {
        return faker.regexify(faker.bothify(faker.resolve("driving_license.usa." + stateAbbreviation))).toUpperCase(Locale.ROOT);
    }
}
