package net.datafaker.idnumbers;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;

import static net.datafaker.idnumbers.Utils.gender;
import static net.datafaker.idnumbers.Utils.birthday;

/**
 * Portuguese VAT identification number (NIF)
 * <p>
 * The number is 9 digits, with modulus 11 checksum digit.
 * There is fixed list of valid first digits to signify different types of NIF numbers
 * <p>
 * See <a href="https://pt.wikipedia.org/wiki/N%C3%BAmero_de_identifica%C3%A7%C3%A3o_fiscal">Número de identificação fiscal</a>
 * and <a href="https://en.wikipedia.org/wiki/VAT_identification_number">VAT identification number</a>
 */
@InternalApi
public class PortugueseIdNumber implements IdNumberGenerator {
    @Override
    public String countryCode() {
        return "PT";
    }

    private static final Character[] VALID_FIRST_DIGITS = {'1', '2', '3', '5', '6', '8'};
    private static final String[] VALID_FIRST_DOUBLE_DIGITS =
        {"45", "70", "71", "72", "74", "75", "77", "79", "90", "91", "98", "99"};

    @Override
    public String generateInvalid(final BaseProviders faker) {
        String digits = faker.number().digits(8);
        int digitSum = calculateDigitSum(digits);
        // by adding 5 to a valid checksum, we should invalidate
        // by having the wrong checksum or just the wrong number of digits
        return digits + (digitSum + 5);
    }

    @Override
    public String generateValid(final BaseProviders faker) {
        String digits;
        if (faker.random().nextBoolean()) {
            final char firstDigit = faker.options().option(VALID_FIRST_DIGITS);
            digits = firstDigit + faker.number().digits(7);
        } else {
            final String firstDoubleDigit = faker.options().option(VALID_FIRST_DOUBLE_DIGITS);
            digits = firstDoubleDigit + faker.number().digits(6);
        }
        int digitSum = calculateDigitSum(digits);
        return digits + digitSum;
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        return new PersonIdNumber(generateValid(faker), birthday(faker, request), gender(faker, request));
    }

    private int calculateDigitSum(String numbers) {
        int checkSum = 0;
        for (int i = 1; i <= numbers.length(); i++) {
            int digit = Character.getNumericValue(numbers.charAt(i - 1));
            checkSum += (10 - i) * digit;
        }

        int val = (checkSum / 11) * 11;
        checkSum -= val;
        if (checkSum == 0 || checkSum == 1) {
            checkSum = 0;
        } else {
            checkSum = 11 - checkSum;
        }
        return checkSum;
    }
}
