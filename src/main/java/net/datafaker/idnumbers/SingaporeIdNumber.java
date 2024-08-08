package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import net.datafaker.providers.base.PersonIdNumber.Gender;
import net.datafaker.service.RandomService;

import java.time.LocalDate;

import static net.datafaker.idnumbers.Utils.gender;
import static net.datafaker.idnumbers.Utils.randomGender;
import static net.datafaker.providers.base.IdNumber.GenderRequest.ANY;

/**
 * Generate number of UIN/FIN for Singapore.
 * Algorithm is given from <a href="http://www.ngiam.net/NRIC/">http://www.ngiam.net/NRIC/</a>
 * See <a href="https://en.wikipedia.org/wiki/National_Registration_Identity_Card">...</a>
 */
public class SingaporeIdNumber implements IdNumberGenerator {
    @Override
    public String countryCode() {
        return "SG";
    }

    public enum Type {SINGAPOREAN_TWENTIETH_CENTURY, FOREIGNER_TWENTIETH_CENTURY, SINGAPOREAN_TWENTY_FIRST_CENTURY, FOREIGNER_TWENTY_FIRST_CENTURY}

    private static String format(LocalDate issueDate, boolean citizen, int[] randomDigits) {
        int checkDigitInitialValue = issueDate.getYear() < 2000 ? 0 : 4;
        char firstLetter = citizen ? centuryPrefixCitizen(issueDate) : centuryPrefixForeigner(issueDate);
        String matchLetters = citizen ? UIN_LETTERS : FIN_LETTERS;
        int checkDigit = checkDigitInitialValue;

        StringBuilder id = new StringBuilder(11);
        id.append(firstLetter);
        for (int i = 0; i < randomDigits.length; i++) {
            checkDigit += randomDigits[i] * CODE[i];
            id.append(randomDigits[i]);
        }
        checkDigit %= 11;
        id.append(matchLetters.charAt(checkDigit));
        return id.toString();
    }

    private static final int[] CODE = {0, 2, 7, 6, 5, 4, 3, 2};
    private static final String FIN_LETTERS = "XWUTRQPNMLK";
    private static final String UIN_LETTERS = "JZIHGFEDCBA";

    @Override
    public String generateValid(BaseProviders faker) {
        return generateValid(faker, new IdNumberRequest(0, 100, ANY)).idNumber();
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        LocalDate birthDate = Utils.birthday(faker, request);
        boolean citizen = faker.bool().bool();
        Gender gender = gender(faker, request);
        return generateValidIdNumber(faker, birthDate, citizen, gender);
    }

    private static PersonIdNumber generateValidIdNumber(BaseProviders faker, LocalDate birthDate, boolean citizen, Gender gender) {
        int[] number = randomDigits(faker);
        String idNumber = format(birthDate, citizen, number);
        return new PersonIdNumber(idNumber, birthDate, gender);
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        return generateValid(faker) + "42";
    }

    public static String getValidFIN(BaseProviders f, Type type) {
        LocalDate birthDate = randomBirthDate(f, type);
        boolean citizen = switch (type) {
            case SINGAPOREAN_TWENTIETH_CENTURY, SINGAPOREAN_TWENTY_FIRST_CENTURY -> true;
            case FOREIGNER_TWENTIETH_CENTURY, FOREIGNER_TWENTY_FIRST_CENTURY -> false;
        };
        return generateValidIdNumber(f, birthDate, citizen, randomGender(f)).idNumber();
    }

    static LocalDate randomBirthDate(BaseProviders faker, Type type) {
        int now = LocalDate.now().getYear();
        return switch (type) {
            case SINGAPOREAN_TWENTIETH_CENTURY,
                 FOREIGNER_TWENTIETH_CENTURY -> faker.timeAndDate().birthday(now - 1900, now - 1999);
            case SINGAPOREAN_TWENTY_FIRST_CENTURY,
                 FOREIGNER_TWENTY_FIRST_CENTURY -> faker.timeAndDate().birthday(now - 2000, now - 2099);
        };
    }

    private static int[] randomDigits(BaseProviders f) {
        final RandomService random = f.random();
        final int[] number = new int[7];
        for (int i = 0; i < number.length; i++) {
            number[i] = random.nextInt(0, 9);
        }
        return number;
    }

    static char centuryPrefixCitizen(LocalDate issueDate) {
        int century = issueDate.getYear() / 100;
        return (char) ('A' + century - 1);
    }

    static char centuryPrefixForeigner(LocalDate issueDate) {
        int century = issueDate.getYear() / 100;
        return (char) ('A' + century - 14);
    }
}
