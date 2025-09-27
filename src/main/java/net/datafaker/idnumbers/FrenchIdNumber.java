package net.datafaker.idnumbers;

import static java.lang.Long.parseLong;
import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber;
import net.datafaker.providers.base.PersonIdNumber;

/**
 * Generates ID numbers for French citizens and Residents
 * <p>
 * See <a href="https://en.wikipedia.org/wiki/INSEE_code">INSEE code</a>.
 */
@InternalApi
public class FrenchIdNumber implements IdNumberGenerator {
    @Override
    public String countryCode() {
        return "FR";
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumber.IdNumberRequest request) {
        LocalDate birthday = birthday(faker, request);
        PersonIdNumber.Gender gender = gender(faker, request);
        String basePart = basePart(faker, birthday, gender);
        return new PersonIdNumber(basePart + controlKey(basePart), birthday, gender);
    }

    /**
     * The id number is 'syymmlloookkk cc'.
     * <ul>
     *   <li>s : gender (1=male, 2=female)</li>
     *   <li>yymm : year and month of birth</li>
     *   <li>llooo : birth department code and city/town code</li>
     *   <li>kkk : birth number for the same date and location</li>
     * </ul>
     *
     * P.S. "ll" comes from <a href="https://en.wikipedia.org/wiki/Departments_of_France">Departments of France</a>
     */
    private String basePart(BaseProviders faker, LocalDate birthday, PersonIdNumber.Gender gender) {
        return genderNumber(gender) + birthDate(birthday) + birthLocation(faker) + birthNumber(faker);
    }

    private String genderNumber(PersonIdNumber.Gender gender) {
        return PersonIdNumber.Gender.MALE.equals(gender) ? "1" : "2";
    }

    private String birthDate(LocalDate birthday) {
        return birthday.format(DateTimeFormatter.ofPattern("yyMM"));
    }

    private String birthLocation(BaseProviders faker) {
        return faker.regexify("\\d{5}|2[AB]\\d{3}|97[1-6]\\d{2}");
    }

    private String birthNumber(BaseProviders faker) {
        return faker.regexify("\\d{3}");
    }

    @InternalApi
    String controlKey(String basePart) {
        long basePartLong = parseLong(basePart.replace("2A","19").replace("2B","18"));
        long controlKey = 97 - (basePartLong % 97);
        return "%02d".formatted(controlKey);
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        return generateValid(faker).substring(0, 13) + "98";
    }
}
