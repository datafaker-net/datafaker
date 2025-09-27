package net.datafaker.idnumbers;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import net.datafaker.providers.base.PersonIdNumber.Gender;

import java.time.LocalDate;

import static net.datafaker.idnumbers.Utils.gender;
import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.multiply;
import static net.datafaker.idnumbers.Utils.randomGender;

/**
 * <a href="https://en.wikipedia.org/wiki/Unique_citizenship_number">Specification</a>
 */
@InternalApi
public class BulgarianIdNumber implements IdNumberGenerator {
    private static final int[] CHECKSUM_WEIGHTS = {2, 4, 8, 5, 10, 9, 7, 3, 6};
    private static final int[] EVEN_DIGITS = {0, 2, 4, 6, 8};
    private static final int[] ODD_DIGITS = {1, 3, 5, 7, 9};

    @Override
    public String countryCode() {
        return "BG";
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        LocalDate birthday = birthday(faker, request);
        Gender gender = gender(faker, request);
        String basePart = basePart(faker, birthday, gender);
        String idNumber = basePart + checksum(basePart);
        return new PersonIdNumber(idNumber, birthday, gender);
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        String basePart = basePart(faker, faker.timeAndDate().birthday(), randomGender(faker));
        return basePart + (checksum(basePart) + 1) % 10;
    }

    private String basePart(BaseProviders faker, LocalDate birthDate, Gender gender) {
        return yy(birthDate) + mm(birthDate) + dd(birthDate) + order(faker, gender);
    }

    private String yy(LocalDate birthDate) {
        return "%02d".formatted(birthDate.getYear() % 100);
    }

    private String mm(LocalDate birthDate) {
        int monthAddition = birthDate.getYear() < 1900 ? 20  :
            birthDate.getYear() >= 2000 ? 40 : 0;
        return "%02d".formatted(birthDate.getMonthValue() + monthAddition);
    }

    private String dd(LocalDate birthDate) {
        return "%02d".formatted(birthDate.getDayOfMonth());
    }

    private String order(BaseProviders faker, Gender gender) {
        int[] availableLastDigits = switch (gender) {
            case FEMALE -> ODD_DIGITS;
            case MALE -> EVEN_DIGITS;
        };
        int lastDigit = availableLastDigits[faker.number().numberBetween(0, 5)];
        return faker.number().digits(2) + lastDigit;
    }

    @InternalApi
    int checksum(String text) {
        int checksum = multiply(text, CHECKSUM_WEIGHTS);
        return (checksum % 11) % 10;
    }
}
