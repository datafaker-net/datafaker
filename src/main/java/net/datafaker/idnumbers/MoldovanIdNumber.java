package net.datafaker.idnumbers;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;

import java.time.LocalDate;

import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.multiply;
import static net.datafaker.idnumbers.Utils.randomGender;

/**
 * The Moldovan Individual Tax ID Number is 13 digits.
 * <p>
 * For Individuals, Example: 4234567891236
 * <a href="https://www.oecd.org/tax/automatic-exchange/crs-implementation-and-assistance/tax-identification-numbers/Moldova-TIN.pdf">Specification</a>
 * <a href="https://taxid.pro/docs/countries/moldova">Overview</a>
 * <a href="https://taxid.pro/?example=moldova-tin-for-individuals">Online generator</a>
 */
@InternalApi
public class MoldovanIdNumber implements IdNumberGenerator {

    private static final int[] CHECKSUM_MASK = {7, 3, 1, 7, 3, 1, 7, 3, 1, 7, 3, 1};

    @Override
    public String countryCode() {
        return "MD";
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        LocalDate birthday = birthday(faker, request);
        String basePart = basePart(faker, birthday);
        String idNumber = basePart + checksum(basePart);
        return new PersonIdNumber(idNumber, birthday, randomGender(faker));
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        String basePart = basePart(faker, faker.timeAndDate().birthday());
        return basePart + (checksum(basePart) + 1) % 10;
    }

    private String basePart(BaseProviders faker, LocalDate birthday) {
        // IDNP: 2ГГГXXXYYYYYK
        return firstDigit() + ГГГ(birthday) + XXX(faker) + YYYYY(faker);
    }

    /**
     * 2 - the identification index of the natural person in the multitude of state identifiers (?)
     */
    private char firstDigit() {
        return '2';
    }

    /**
     * ГГГ- the last three digits of the IDNP award year
     */
    private String ГГГ(LocalDate birthday) {
        return "%03d".formatted(birthday.getYear() % 1000);
    }

    /**
     * XXX - code of the registrar's office
     */
    private String XXX(BaseProviders faker) {
        return faker.number().digits(3);
    }

    /**
     * YYYYY- the order number of the registration in the respective year in the respective office
     */
    private String YYYYY(BaseProviders faker) {
        return faker.number().digits(5);
    }

    @InternalApi
    char checksum(String text) {
        int checksum = multiply(text, CHECKSUM_MASK);
        return (char) ('0' + checksum % 10);
    }
}
