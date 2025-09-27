package net.datafaker.idnumbers;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber;
import net.datafaker.providers.base.PersonIdNumber;

import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.gender;

/**
 * Generates ID numbers for Georgian citizens and Residents
 */
@InternalApi
public class GeorgianIdNumber implements IdNumberGenerator {
    @Override
    public String countryCode() {
        return "GE";
    }

    @Override
    public String generateValid(BaseProviders faker) {
        return faker.numerify("###########");
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumber.IdNumberRequest request) {
        return new PersonIdNumber(
            generateValid(faker),
            birthday(faker, request),
            gender(faker, request)
        );
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        return faker.numerify("###########42");
    }
}
