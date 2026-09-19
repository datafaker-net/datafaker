package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;

import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.gender;

/**
 * Spanish national identity number (DNI/NIF).
 * See <a href="https://www.interior.gob.es/opencms/es/servicios-al-ciudadano/tramites-y-gestiones/dni/calculo-del-digito-de-control-del-nif-nie/">
 * Calculation of the NIF/NIE check character</a>.
 */
public class SpanishIdNumber implements IdNumberGenerator {
    private static final String CHECK_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";

    @Override
    public String countryCode() {
        return "ES";
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        String digits = faker.number().digits(8);
        String idNumber = digits + checksum(digits);
        return new PersonIdNumber(idNumber, birthday(faker, request), gender(faker, request));
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        String digits = faker.number().digits(8);
        char validLetter = checksum(digits);
        int validLetterIndex = CHECK_LETTERS.indexOf(validLetter);
        char invalidLetter = CHECK_LETTERS.charAt((validLetterIndex + 1) % CHECK_LETTERS.length());
        return digits + invalidLetter;
    }

    static char checksum(String digits) {
        int number = Integer.parseInt(digits);
        return CHECK_LETTERS.charAt(number % CHECK_LETTERS.length());
    }
}
