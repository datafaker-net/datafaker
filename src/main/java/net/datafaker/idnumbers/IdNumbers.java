package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;

import java.time.format.DateTimeFormatter;

public interface IdNumbers {
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    /**
     * @return ISO-2 code of the country ("US" for America, "EE" for Estonia etc.)
     */
    String country();

    /**
     * Generates a valid ID number for given country (a.k.a. "SSN", "Personal code" etc.)
     */
    String generateValid(BaseProviders faker);

    /**
     * Generates an invalid ID number for given country (a.k.a. "SSN", "Personal code" etc.)
     */
    String generateInvalid(BaseProviders faker);
}
