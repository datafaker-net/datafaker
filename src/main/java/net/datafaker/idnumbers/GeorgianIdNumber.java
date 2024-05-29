package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;

/**
 * Generates ID numbers for Georgian citizens and Residents
 */
public class GeorgianIdNumber implements IdNumbers {
    @Override
    public String countryCode() {
        return "GE";
    }

    @Override
    public String generateValid(BaseProviders faker) {
        return faker.numerify("###########");
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        return faker.numerify("###########42");
    }
}
