package net.datafaker.idnumbers;

import static java.util.Locale.ROOT;
import static java.util.Map.entry;
import static net.datafaker.idnumbers.LatinLetters.isConsonant;
import static net.datafaker.idnumbers.LatinLetters.removeNonLatinLetters;
import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.gender;
import static net.datafaker.idnumbers.Utils.join;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.IntStream;

import net.datafaker.annotations.InternalApi;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import net.datafaker.providers.base.PersonIdNumber.Gender;
import net.datafaker.providers.base.Text.TextSymbolsBuilder;

/**
 * See <a href="https://codicefiscale.com/#calcolo-completato">Italian national id numbers</a>
 */
@InternalApi
public class ItalianIdNumber implements IdNumberGenerator {

    private static final String REGION_CODE_FIRST_LETTERS = "ABCDEFGHIJKLM";
    private static final String MONTH_LETTER = "_ABCDEHLMPRST";

    private static final Map<Character, Integer> ODD_CHARACTERS = Map.ofEntries(
        entry('0', 1), entry('C', 5), entry('O', 11),
        entry('1', 0), entry('D', 7), entry('P', 3),
        entry('2', 5), entry('E', 9), entry('Q', 6),
        entry('3', 7), entry('F', 13), entry('R', 8),
        entry('4', 9), entry('G', 15), entry('S', 12),
        entry('5', 13), entry('H', 17), entry('T', 14),
        entry('6', 15), entry('I', 19), entry('U', 16),
        entry('7', 17), entry('J', 21), entry('V', 10),
        entry('8', 19), entry('K', 2), entry('W', 22),
        entry('9', 21), entry('L', 4), entry('X', 25),
        entry('A', 1), entry('M', 18), entry('Y', 24),
        entry('B', 0), entry('N', 20), entry('Z', 23)
    );

    private static final Map<Character, Integer> EVEN_CHARACTERS = Map.ofEntries(
        entry('0', 0), entry('C', 2), entry('O', 14),
        entry('1', 1), entry('D', 3), entry('P', 15),
        entry('2', 2), entry('E', 4), entry('Q', 16),
        entry('3', 3), entry('F', 5), entry('R', 17),
        entry('4', 4), entry('G', 6), entry('S', 18),
        entry('5', 5), entry('H', 7), entry('T', 19),
        entry('6', 6), entry('I', 8), entry('U', 20),
        entry('7', 7), entry('J', 9), entry('V', 21),
        entry('8', 8), entry('K', 10), entry('W', 22),
        entry('9', 9), entry('L', 11), entry('X', 23),
        entry('A', 0), entry('M', 12), entry('Y', 24),
        entry('B', 1), entry('N', 13), entry('Z', 25)
    );

    private static final Map<Integer, Character> DIVISION_REST = Map.ofEntries(
        entry(0, 'A'), entry(10, 'K'), entry(20, 'U'),
        entry(1, 'B'), entry(11, 'L'), entry(21, 'V'),
        entry(2, 'C'), entry(12, 'M'), entry(22, 'W'),
        entry(3, 'D'), entry(13, 'N'), entry(23, 'X'),
        entry(4, 'E'), entry(14, 'O'), entry(24, 'Y'),
        entry(5, 'F'), entry(15, 'P'), entry(25, 'Z'),
        entry(6, 'G'), entry(16, 'Q'),
        entry(7, 'H'), entry(17, 'R'),
        entry(8, 'I'), entry(18, 'S'),
        entry(9, 'J'), entry(19, 'T')
    );

    @Override
    public String countryCode() {
        return "IT";
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        LocalDate birthday = birthday(faker, request);
        Gender gender = gender(faker, request);
        String firstName = faker.name().firstName().toUpperCase(ROOT);
        String lastName = faker.name().lastName().toUpperCase(ROOT);
        String basePart = encodeName(firstName) + encodeName(lastName) + encodeYear(birthday) + encodeMonth(birthday) + encodeDayAndGender(birthday, gender) + encodeRegion(faker);
        return new PersonIdNumber(basePart + checksum(basePart), birthday, gender);
    }

    /**
     * The first three letters are taken from the consonants of the name.
     * In case of insufficient consonants the vowels are also taken, but they always come after the consonants.
     * In case of too short names, "X" letter is appended.
     *
     * @param name first name of last name, UPPER CASE!
     * @return 3 letters from the name
     */
    @InternalApi
    String encodeName(String name) {
        String latinLetters = removeNonLatinLetters(name);
        IntStream consonants = latinLetters.chars().filter(c -> isConsonant(c));
        IntStream vowels = latinLetters.chars().filter(c -> !isConsonant(c));
        IntStream placeholder = "XXX".chars();
        return join(consonants, vowels, placeholder, 3);
    }

    private String encodeYear(LocalDate birthday) {
        return String.valueOf(birthday.getYear()).substring(2);
    }

    private char encodeMonth(LocalDate birthday) {
        return MONTH_LETTER.charAt(birthday.getMonthValue());
    }

    private String encodeDayAndGender(LocalDate birthday, Gender gender) {
        int day = switch (gender) {
            case FEMALE -> 40 + birthday.getDayOfMonth();
            case MALE -> birthday.getDayOfMonth();
        };
        return "%02d".formatted(day);
    }

    private String encodeRegion(BaseProviders faker) {
        String regionLetter = faker.text().text(TextSymbolsBuilder.builder().len(1).with(REGION_CODE_FIRST_LETTERS).build());
        return "%s%03d".formatted(regionLetter, faker.number().numberBetween(1, 1000));
    }

    @Override
    public String generateInvalid(BaseProviders faker) {
        String valid = generateValid(faker);
        return valid.substring(0, valid.length() - 1) + '9';
    }

    @InternalApi
    char checksum(String basePart) {
        int sum = 0;
        for (int i = 0; i < basePart.length(); i += 2) {
            sum += ODD_CHARACTERS.get(basePart.charAt(i));
        }
        for (int i = 1; i < basePart.length(); i += 2) {
            sum += EVEN_CHARACTERS.get(basePart.charAt(i));
        }
        return DIVISION_REST.get(sum % 26);
    }

}
