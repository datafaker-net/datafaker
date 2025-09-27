package net.datafaker.idnumbers;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber;
import net.datafaker.providers.base.PersonIdNumber;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static net.datafaker.idnumbers.Utils.gender;
import static net.datafaker.idnumbers.Utils.multiply;

/**
 * The Ukrainian Identity Number (UNZR)
 * is a unique registration record number of 13 digits in a form "YYYYMMDD-XXXXC"
 * where C is a control digit calculated from all the other 12 digits in the UNZR
 * <a href="https://blog.uaid.net.ua/ua-id-passport-outside/">algorithm to validate UNZR code</a>
 */
@InternalApi
public class UkrainianIdNumber implements IdNumberGenerator {
    private static final int[] CHECKSUM_WEIGHTS = {7, 3, 1, 7, 3, 1, 7, 3, 1, 7, 3, 1};

    @Override
    public String countryCode() {
        return "UA";
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        LocalDate birthday = faker.timeAndDate().birthday();
        String dob = DateTimeFormatter.ofPattern("yyyyMMdd").format(birthday);
        String numbers = faker.numerify("####");

        int multiplied = multiply(dob + numbers, CHECKSUM_WEIGHTS);
        int checksum = (multiplied + 1) % 10;
        return dob + "-" + numbers + checksum;
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumber.IdNumberRequest request) {
        LocalDate birthday = faker.timeAndDate().birthday();
        String dob = DateTimeFormatter.ofPattern("yyyyMMdd").format(birthday);
        String numbers = faker.numerify("####");
        int checksum = multiply(dob + numbers, CHECKSUM_WEIGHTS) % 10;
        String unzr = dob + "-" + numbers + checksum;
        return new PersonIdNumber(unzr, birthday, gender(faker, request));
    }
}
