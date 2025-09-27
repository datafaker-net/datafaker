package net.datafaker.idnumbers;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import net.datafaker.providers.base.PersonIdNumber.Gender;

import java.time.LocalDate;

import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.digit;
import static net.datafaker.idnumbers.Utils.digitAt;
import static net.datafaker.idnumbers.Utils.gender;
import static net.datafaker.providers.base.PersonIdNumber.Gender.FEMALE;

/**
 * The Albanian Identity Number is a unique personal identification number of 10 characters in the format YYMMDDSSSC
 */
@InternalApi
public class AlbanianIdNumber implements IdNumberGenerator {
    private static final String FIRST_CHAR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String CHECKSUM_CHAR = "WABCDEFGHIJKLMNOPQRSTUV";

    @Override
    public String countryCode() {
        return "AL";
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        String pin = generateValid(faker);
        int invalidMonth = faker.number().numberBetween(93, 99);
        return pin.substring(0, 2) + invalidMonth + pin.substring(4);
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        LocalDate birthDate = birthday(faker, request);
        Gender gender = gender(faker, request);
        String basePart = yy(birthDate.getYear()) + mm(birthDate.getMonthValue(), gender) + dd(birthDate.getDayOfMonth()) + sss(faker);
        String idNumber = basePart + checksum(basePart);
        return new PersonIdNumber(idNumber, birthDate, gender);
    }

    @InternalApi
    String yy(int year) {
        return FIRST_CHAR.charAt((year - 1800) / 10) + String.valueOf(year % 10);
    }

    @InternalApi
    String mm(int month, Gender gender) {
        return String.format("%02d", (gender == FEMALE ? 50 : 0) + month);
    }

    @InternalApi
    String dd(int dayOfMonth) {
        return String.format("%02d", dayOfMonth);
    }

    private String sss(BaseProviders faker) {
        return faker.number().digits(3);
    }

    @InternalApi
    char checksum(String text) {
        int checksum = checksumOfFirstChar(text.charAt(0));
        for (int i = 1; i < text.length(); i++) {
            checksum += digitAt(text, i) * i;
        }
        return CHECKSUM_CHAR.charAt(checksum % 23);
    }

    @InternalApi
    int checksumOfFirstChar(char c) {
        return Character.isLetter(c) ? CHECKSUM_CHAR.indexOf(c) : digit(c);
    }
}
