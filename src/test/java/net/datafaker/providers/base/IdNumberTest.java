package net.datafaker.providers.base;

import static net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil.isCPFValid;
import static net.datafaker.providers.base.IdNumber.GenderRequest.ANY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import static java.lang.Integer.parseInt;
import static java.time.format.DateTimeFormatter.ofPattern;

import net.datafaker.Faker;
import net.datafaker.helpers.IdNumberPatterns;
import net.datafaker.idnumbers.SouthAfricanIdNumber;
import net.datafaker.idnumbers.SwedenIdNumber;
import net.datafaker.junit.FakerSource;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import org.assertj.core.api.AbstractStringAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Locale;
import java.util.regex.Pattern;

@TestInstance(PER_CLASS)
class IdNumberTest {

    private final Faker faker = new Faker();

    @Test
    void testValid() {
        assertThat(faker.idNumber().valid()).matches("[0-8]\\d{2}-\\d{2}-\\d{4}");
    }

    @Test
    void testInvalid() {
        assertThat(faker.idNumber().invalid()).matches("[0-9]\\d{2}-\\d{2}-\\d{4}");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#ssnValid", repeat = 100)
    void testSsnValid(String validSsn) {
        assertThat(validSsn).matches("[0-8]\\d{2}-\\d{2}-\\d{4}");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "en-US", repeat = 100)
    void usIdNumber(String idNumber) {
        assertThat(idNumber).matches("[0-8]\\d{2}-\\d{2}-\\d{4}");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#invalid", locale = "en-US", repeat = 100)
    void testSsnInvalid(String invalidSsn) {
        if (!invalidSsn.startsWith("9")) {
            assertThat(invalidSsn).matches("[0-8]\\d{2}-\\d{2}-\\d{4}");
        }
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#singaporeanFin", repeat = 100)
    void testSingaporeanFin(String id) {
        assertThat(id).matches("G[0-9]{7}[A-Z]");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#singaporeanFinBefore2000", repeat = 100)
    void testSingaporeanFinBefore2000(String id) {
        assertThat(id).matches("F[0-9]{7}[A-Z]");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#singaporeanUin", repeat = 100)
    void testSingaporeanUin(String id) {
        assertThat(id).matches("T[0-9]{7}[A-Z]");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#singaporeanUinBefore2000", repeat = 100)
    void testSingaporeanUinBefore2000(String id) {
        assertThat(id).matches("S[0-9]{7}[A-Z]");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#peselNumber", repeat = 100)
    void testPeselNumber(String id) {
        assertThat(id).matches("[0-9]{11}");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "sv-SE", repeat = 100)
    void testValidSwedishSsn(String actual) {
        assertThat(actual).matches(IdNumberPatterns.SWEDISH);
        assertThat(SwedenIdNumber.isValidSwedishSsn(actual)).isTrue();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#invalid", locale = "sv-SE", repeat = 100)
    void testInvalidSwedishSsn(String actual) {
        assertThat(actual).matches(IdNumberPatterns.SWEDISH);
        assertThat(SwedenIdNumber.isValidSwedishSsn(actual)).isFalse();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "en-ZA", repeat = 100)
    void southAfrica_valid(String actual) {
        assertThat(actual).matches(IdNumberPatterns.SOUTH_AFRICAN);
        assertThat(SouthAfricanIdNumber.isValidEnZASsn(actual)).isTrue();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#invalid", locale = "en-ZA", repeat = 100)
    void southAfrica_invalid(String actual) {
        assertThat(actual).matches(IdNumberPatterns.SOUTH_AFRICAN);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "et-EE", repeat = 100)
    void estonianPersonalCode_valid(String pin) {
        assertThatPinMatches(pin, IdNumberPatterns.ESTONIAN);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#invalid", locale = "et-EE", repeat = 100)
    void estonianPersonalCode_invalid(String pin) {
        assertThatPinMatches(pin, IdNumberPatterns.ESTONIAN);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "pt-BR", repeat = 100)
    void brazilianPersonalCode_valid(String actual) {
        assertThatPinMatches(actual, IdNumberPatterns.BRAZILIAN);
        assertThat(isCPFValid(actual)).describedAs(() -> "Current value " + actual).isTrue();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#invalid", locale = "pt-BR", repeat = 100)
    void brazilianPersonalCode_invalid(String actual) {
        assertThatPinMatches(actual, IdNumberPatterns.BRAZILIAN);
        assertThat(isCPFValid(actual)).describedAs(() -> "Current value " + actual).isFalse();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber", locale = "lv-LV", repeat = 100)
    void latvianPersonalCode_valid_oldFormat_before_2017_07_01(IdNumber idNumber) {
        int minAge = LocalDate.now().getYear() - 2017 + 1;
        String idn = idNumber.valid(new IdNumberRequest(minAge, minAge + 50, ANY)).idNumber();
        assertThatPinMatches(idn, "[0-3]\\d[0-1]\\d{3}-[0-2]\\d{4}");
        assertThatCode(() -> LocalDate.parse(idn.substring(0, 6), ofPattern("ddMMyy"))).doesNotThrowAnyException();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber", locale = "lv-LV", repeat = 100)
    void latvianPersonalCode_valid_newFormat_since_2017_07_01(IdNumber idNumber) {
        int maxAge = LocalDate.now().getYear() - 2017 - 1;
        assertThatPin(idNumber.valid(new IdNumberRequest(0, maxAge, ANY)).idNumber()).matches("3[2-9]\\d{9}");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#invalid", locale = "lv-LV", repeat = 100)
    void latvianPersonalCode_invalid(String pin) {
        assertThatPinMatches(pin, "[0-3]\\d[0-1]\\d{3}-[0-2]\\d{4}");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "sq-AL", repeat = 100)
    void albanianPersonalCode_valid(String pin) {
        assertThatPinMatches(pin, "\\w\\d{8}\\w");
        assertThat(parseInt(pin.substring(2, 4)) % 50)
            .as(() -> "Valid PIN %s should have month number between 1..12 (for males) or 51..62 (for females)".formatted(pin))
            .isBetween(1, 12);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#invalid", locale = "sq-AL", repeat = 100)
    void albanianPersonalCode_invalid(String pin) {
        assertThatPinMatches(pin, "\\w\\d{8}\\w");
        assertThat(parseInt(pin.substring(2, 4)))
            .as(() -> "Invalid PIN %s should have month greater than (any month + 50)".formatted(pin))
            .isGreaterThan(62);
    }

    @ParameterizedTest
    @ValueSource(strings = {"en", "ro", "ru"})
    void moldovaPersonalCode_valid(String language) {
        final var localFaker = new Faker(new Locale(language, "MD"));
        for (int i = 0; i < 100; i++) {
            assertThatPin(localFaker.idNumber().valid()).matches("\\d{13}");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"en", "ro", "ru"})
    void moldovaPersonalCode_invalid(String language) {
        final var localFaker = new Faker(new Locale(language, "MD"));
        for (int i = 0; i < 100; i++) {
            assertThatPin(localFaker.idNumber().invalid()).matches("\\d{13}");
        }
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "bg-BG", repeat = 100)
    void bulgarianPersonalCode_valid(String pin) {
        assertThatPinMatches(pin, "\\d{10}");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#invalid", locale = "bg-BG", repeat = 100)
    void bulgarianPersonalCode_invalid(String pin) {
        assertThatPinMatches(pin, "\\d{10}");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "mk-MK", repeat = 100)
    void macedonianPersonalCode_valid(String pin) {
        assertThatPinMatches(pin, "\\d{13}");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#invalid", locale = "mk-MK", repeat = 100)
    void macedonianPersonalCode_invalid(String pin) {
        assertThatPinMatches(pin, "\\d{13}");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "ro-RO", repeat = 100)
    void romanianPersonalCode_valid(String pin) {
        assertThatPinMatches(pin, "\\d{13}");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "uk-UA", repeat = 100)
    void ukrainianUznr_valid(String pin) {
        assertThatPinMatches(pin, IdNumberPatterns.UKRAINIAN);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#invalid", locale = "uk-UA", repeat = 100)
    void ukrainianUznr_invalid(String pin) {
        assertThatPinMatches(pin, IdNumberPatterns.UKRAINIAN);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "fr-FR", repeat = 10)
    void frenchIdNumber(String idNumber) {
        assertThat(idNumber).matches(IdNumberPatterns.FRENCH);
    }

    @Test
    void italianIdNumberSample() {
        assertThatPin("PTRJHN89T04Z222B").matches(IdNumberPatterns.ITALIAN);
        assertThatPin("BBBTTT20H12X122H").matches(IdNumberPatterns.ITALIAN);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "it-IT", repeat = 100)
    void italianIdNumber(String pin) {
        assertThatPinMatches(pin, IdNumberPatterns.ITALIAN);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "hu-HU", repeat = 100)
    void hungarianIdNumber_valid(String pin) {
        assertThatPinMatches(pin, IdNumberPatterns.HUNGARIAN);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#invalid", locale = "hu-HU", repeat = 100)
    void hungarianIdNumber_invalid(String pin) {
        assertThatPinMatches(pin, IdNumberPatterns.HUNGARIAN);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#valid", locale = "no-NO", repeat = 100)
    void norwegianIdNumber_valid(String pin) {
        assertThatPinMatches(pin, IdNumberPatterns.NORWEGIAN);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "idNumber#invalid", locale = "no-NO", repeat = 100)
    void norwegianIdNumber_invalid(String pin) {
        assertThatPinMatches(pin, IdNumberPatterns.NORWEGIAN);
    }

    @Deprecated
    private static AbstractStringAssert<?> assertThatPin(String pin) {
        return assertThat(pin).as(() -> "PIN: %s".formatted(pin));
    }

    private static void assertThatPinMatches(String pin, String regex) {
        assertThatPinMatches(pin, Pattern.compile(regex));
    }

    private static void assertThatPinMatches(String pin, Pattern pattern) {
        assertThat(pin).as(() -> "PIN: %s".formatted(pin)).matches(pattern);
    }

}
