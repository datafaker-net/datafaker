package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;

import java.time.format.DateTimeFormatter;

public interface IdNumbers {
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    /**
     * ISO-2 code of the country this generator provides ID numbers for
     *
     * @return e.g. "US" for America, "EE" for Estonia, "MD" for Moldova etc.
     */
    String countryCode();

    /**
     * Generates a valid ID number for given country (a.k.a. "SSN", "Personal code" etc.)
     */
    String generateValid(BaseProviders faker);

    /**
     * Generates an invalid ID number for given country (a.k.a. "SSN", "Personal code" etc.)
     */
    String generateInvalid(BaseProviders faker);
}
