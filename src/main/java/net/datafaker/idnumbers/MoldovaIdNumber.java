package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;

import java.time.LocalDate;

/**
 * The Moldovan Individual Tax ID Number is 13 digits.
 * <p>
 * For Individuals, Example: 4234567891236
 * <a href="https://www.oecd.org/tax/automatic-exchange/crs-implementation-and-assistance/tax-identification-numbers/Moldova-TIN.pdf">Specification</a>
 * <a href="https://taxid.pro/docs/countries/moldova">Overview</a>
 * <a href="https://taxid.pro/?example=moldova-tin-for-individuals">Online generator</a>
 */
public class MoldovaIdNumber implements IdNumbers {

    private static final int[] CHECKSUM_MASK = new int[]{7, 3, 1, 7, 3, 1, 7, 3, 1, 7, 3, 1};

    public String getValid(BaseProviders faker) {
        String basePart = basePart(faker);
        return basePart + checksum(basePart);
    }

    public String getInvalid(BaseProviders faker) {
        String basePart = basePart(faker);
        return basePart + (checksum(basePart) + 1) % 10;
    }

    private String basePart(BaseProviders faker) {
        var birthday = faker.timeAndDate().birthday(0, 120);
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

    char checksum(String text) {
        int checksum = 0;
        for (int i = 0; i < text.length(); i++) {
            checksum += digitAt(text, i) * CHECKSUM_MASK[i];
        }
        return (char) ('0' + checksum % 10);
    }

    private int digitAt(String text, int index) {
        return text.charAt(index) - '0';
    }
}
