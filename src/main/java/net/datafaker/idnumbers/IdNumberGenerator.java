package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;

import static net.datafaker.providers.base.IdNumber.GenderRequest.ANY;

public interface IdNumberGenerator {
    /**
     * ISO-2 code of the country this generator provides ID numbers for
     *
     * @return e.g. "US" for America, "EE" for Estonia, "MD" for Moldova etc.
     */
    String countryCode();

    /**
     * Generates a valid ID number for given country (a.k.a. "SSN", "Personal code" etc.)
     */
    default String generateValid(BaseProviders faker) {
        return generateValid(faker, new IdNumberRequest(18, 65, ANY)).idNumber();
    }

    /**
     * Generates an invalid ID number for given country (a.k.a. "SSN", "Personal code" etc.)
     */
    String generateInvalid(BaseProviders faker);

    /**
     * Generates a valid ID number for given country corresponding to given criterias (age range, gender etc.)
     *
     * @return PersonIdNumber containing a valid combination of ID, Birthday and Gender.
     * In countries where ID number doesn't contain gender and/or birthday, the latter values are just random.
     */
    PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request);
}
