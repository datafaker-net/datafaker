package net.datafaker.providers.base;

import net.datafaker.Faker;
import net.datafaker.helpers.IdNumberPatterns;
import net.datafaker.idnumbers.SouthAfricanIdNumber;
import net.datafaker.idnumbers.SwedenIdNumber;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import org.assertj.core.api.AbstractStringAssert;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Locale;

import static java.lang.Integer.parseInt;
import static java.time.format.DateTimeFormatter.ofPattern;
import static net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil.isCPFValid;
import static net.datafaker.providers.base.IdNumber.GenderRequest.ANY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class IdNumberTest {
    private final Faker faker = new Faker();

    private static final Faker SWEDISH = new Faker(new Locale("sv", "SE"));
    private static final Faker SOUTH_AFRICA = new Faker(new Locale("en", "ZA"));
    private static final Faker US = new Faker(new Locale("en", "US"));
    private static final Faker ESTONIAN = new Faker(new Locale("et", "EE"));
    private static final Faker LATVIAN = new Faker(new Locale("lv", "LV"));
    private static final Faker ALBANIAN = new Faker(new Locale("sq", "AL"));
    private static final Faker BULGARIAN = new Faker(new Locale("bg", "BG"));
    private static final Faker MACEDONIAN = new Faker(new Locale("mk", "MK"));
    private static final Faker ROMANIAN = new Faker(new Locale("ro", "RO"));
    private static final Faker UKRAINIAN = new Faker(new Locale("uk", "UA"));
    private static final Faker FRENCH = new Faker(new Locale("fr", "FR"));
    private static final Faker ITALIAN = new Faker(new Locale("it", "IT"));
    private static final Faker HUNGARIAN = new Faker(new Locale("hu", "HU"));
    private static final Faker BRAZILIAN = new Faker(new Locale("pt", "BR"));

    @Test
    void testValid() {
        assertThat(faker.idNumber().valid()).matches("[0-8]\\d{2}-\\d{2}-\\d{4}");
    }

    @Test
    void testInvalid() {
        assertThat(faker.idNumber().invalid()).matches("[0-9]\\d{2}-\\d{2}-\\d{4}");
    }

    @RepeatedTest(100)
    void testSsnValid() {
        assertThat(faker.idNumber().ssnValid()).matches("[0-8]\\d{2}-\\d{2}-\\d{4}");
        assertThat(US.idNumber().valid()).matches("[0-8]\\d{2}-\\d{2}-\\d{4}");
    }

    @RepeatedTest(100)
    void testSsnInvalid() {
        String invalidSsn = US.idNumber().invalid();
        if (!invalidSsn.startsWith("9")) {
            assertThat(invalidSsn).matches("[0-8]\\d{2}-\\d{2}-\\d{4}");
        }
    }

    @RepeatedTest(100)
    void testValidSwedishSsn() {
        String actual = SWEDISH.idNumber().valid();
        assertThat(actual).matches(IdNumberPatterns.SWEDISH);
        assertThat(SwedenIdNumber.isValidSwedishSsn(actual)).isTrue();
    }

    @RepeatedTest(100)
    void testInvalidSwedishSsn() {
        String actual = SWEDISH.idNumber().invalid();
        assertThat(actual).matches(IdNumberPatterns.SWEDISH);
        assertThat(SwedenIdNumber.isValidSwedishSsn(actual)).isFalse();
    }

    @RepeatedTest(100)
    void southAfrica_valid() {
        String actual = SOUTH_AFRICA.idNumber().valid();
        assertThat(actual).matches(IdNumberPatterns.SOUTH_AFRICAN);
        assertThat(SouthAfricanIdNumber.isValidEnZASsn(actual)).isTrue();
    }

    @RepeatedTest(100)
    void southAfrica_invalid() {
        assertThat(SOUTH_AFRICA.idNumber().invalid()).matches(IdNumberPatterns.SOUTH_AFRICAN);
    }

    @RepeatedTest(100)
    void testSingaporeanFin() {
        assertThat(faker.idNumber().singaporeanFin()).matches("G[0-9]{7}[A-Z]");
    }

    @RepeatedTest(100)
    void testSingaporeanFinBefore2000() {
        assertThat(faker.idNumber().singaporeanFinBefore2000()).matches("F[0-9]{7}[A-Z]");
    }

    @RepeatedTest(100)
    void testSingaporeanUin() {
        assertThat(faker.idNumber().singaporeanUin()).matches("T[0-9]{7}[A-Z]");
    }

    @RepeatedTest(100)
    void testSingaporeanUinBefore2000() {
        assertThat(faker.idNumber().singaporeanUinBefore2000()).matches("S[0-9]{7}[A-Z]");
    }

    @RepeatedTest(100)
    @SuppressWarnings("deprecation")
    void testPeselNumber() {
        assertThat(faker.idNumber().peselNumber()).matches("[0-9]{11}");
    }

    @RepeatedTest(100)
    void estonianPersonalCode_valid() {
        assertThatPin(ESTONIAN.idNumber().valid()).matches(IdNumberPatterns.ESTONIAN);
    }

    @RepeatedTest(100)
    void estonianPersonalCode_invalid() {
        assertThatPin(ESTONIAN.idNumber().invalid()).matches(IdNumberPatterns.ESTONIAN);
    }

    @RepeatedTest(100)
    void brazilianPersonalCode_valid() {
        String actual = BRAZILIAN.idNumber().valid();
        assertThatPin(actual).matches(IdNumberPatterns.BRAZILIAN);
        assertThat(isCPFValid(actual)).describedAs(() -> "Current value " + actual).isTrue();
    }

    @RepeatedTest(100)
    void brazilianPersonalCode_invalid() {
        String actual = BRAZILIAN.idNumber().invalid();
        assertThatPin(actual).matches(IdNumberPatterns.BRAZILIAN);
        assertThat(isCPFValid(actual)).describedAs(() -> "Current value " + actual).isFalse();
    }

    @RepeatedTest(100)
    @SuppressWarnings("ResultOfMethodCallIgnored")
    void latvianPersonalCode_valid_oldFormat_before_2017_07_01() {
        int minAgeApplicableForOldFormat = LocalDate.now().getYear() - 2017 + 1;
        String idNumber = LATVIAN.idNumber().valid(new IdNumberRequest(minAgeApplicableForOldFormat, minAgeApplicableForOldFormat + 50, ANY)).idNumber();
        assertThatPin(idNumber).matches("[0-3]\\d[0-1]\\d{3}-[0-2]\\d{4}");
        assertThatCode(() -> LocalDate.parse(idNumber.substring(0, 6), ofPattern("ddMMyy"))).doesNotThrowAnyException();
    }

    @RepeatedTest(100)
    void latvianPersonalCode_valid_newFormat_since_2017_07_01() {
        int maxAgeApplicableForNewFormat = LocalDate.now().getYear() - 2017 - 1;
        assertThatPin(LATVIAN.idNumber().valid(new IdNumberRequest(0, maxAgeApplicableForNewFormat, ANY)).idNumber()).matches("3[2-9]\\d{9}");
    }

    @RepeatedTest(100)
    void latvianPersonalCode_invalid() {
        assertThatPin(LATVIAN.idNumber().invalid()).matches("[0-3]\\d[0-1]\\d{3}-[0-2]\\d{4}");
    }

    @RepeatedTest(100)
    void albanianPersonalCode_valid() {
        String pin = ALBANIAN.idNumber().valid();
        assertThatPin(pin).matches("\\w\\d{8}\\w");
        assertThat(parseInt(pin.substring(2, 4)) % 50)
            .as(() -> "Valid PIN %s should have month number between 1..12 (for males) or 51..62 (for females)".formatted(pin))
            .isBetween(1, 12);
    }

    @RepeatedTest(100)
    void albanianPersonalCode_invalid() {
        String pin = ALBANIAN.idNumber().invalid();
        assertThatPin(pin).matches("\\w\\d{8}\\w");
        assertThat(parseInt(pin.substring(2, 4)))
            .as(() -> "Invalid PIN %s should have month greater than (any month + 50)".formatted(pin))
            .isGreaterThan(62);
    }

    @ParameterizedTest
    @ValueSource(strings = {"en", "ro", "ru"})
    void moldovaPersonalCode_valid(String language) {
        final var faker = new Faker(new Locale(language, "MD"));
        for (int i = 0; i < 100; i++) {
            String pin = faker.idNumber().valid();
            assertThatPin(pin).matches("\\d{13}");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"en", "ro", "ru"})
    void moldovaPersonalCode_invalid(String language) {
        final var faker = new Faker(new Locale(language, "MD"));
        for (int i = 0; i < 100; i++) {
            String pin = faker.idNumber().invalid();
            assertThatPin(pin).matches("\\d{13}");
        }
    }

    @RepeatedTest(100)
    void bulgarianPersonalCode_valid() {
        String pin = BULGARIAN.idNumber().valid();
        assertThatPin(pin).matches("\\d{10}");
    }

    @RepeatedTest(100)
    void bulgarianPersonalCode_invalid() {
        String pin = BULGARIAN.idNumber().invalid();
        assertThatPin(pin).matches("\\d{10}");
    }

    @RepeatedTest(100)
    void macedonianPersonalCode_valid() {
        String pin = MACEDONIAN.idNumber().valid();
        assertThatPin(pin).matches("\\d{13}");
    }

    @RepeatedTest(100)
    void romanianPersonalCode_valid() {
        String pin = ROMANIAN.idNumber().valid();
        assertThatPin(pin).matches("\\d{13}");
    }

    @RepeatedTest(100)
    void macedonianPersonalCode_invalid() {
        String pin = MACEDONIAN.idNumber().invalid();
        assertThatPin(pin).matches("\\d{13}");
    }

    @RepeatedTest(100)
    void ukrainianUznr_valid() {
        assertThatPin(UKRAINIAN.idNumber().valid()).matches(IdNumberPatterns.UKRAINIAN);
    }

    @RepeatedTest(100)
    void ukrainianUznr_invalid() {
        assertThatPin(UKRAINIAN.idNumber().invalid()).matches(IdNumberPatterns.UKRAINIAN);
    }

    @Test
    void frenchIdNumber() {
        String actual = FRENCH.idNumber().valid();
        assertThat(actual).matches(IdNumberPatterns.FRENCH);
    }

    @Test
    void italianIdNumberSample() {
        assertThatPin("PTRJHN89T04Z222B").matches(IdNumberPatterns.ITALIAN);
        assertThatPin("BBBTTT20H12X122H").matches(IdNumberPatterns.ITALIAN);
    }

    @RepeatedTest(100)
    void italianIdNumber() {
        assertThatPin(ITALIAN.idNumber().valid()).matches(IdNumberPatterns.ITALIAN);
    }

    @RepeatedTest(100)
    void hungarianIdNumber_valid() {
        assertThatPin(HUNGARIAN.idNumber().valid()).matches(IdNumberPatterns.HUNGARIAN);
    }

    @RepeatedTest(100)
    void hungarianIdNumber_invalid() {
        assertThatPin(HUNGARIAN.idNumber().invalid()).matches(IdNumberPatterns.HUNGARIAN);
    }

    private static AbstractStringAssert<?> assertThatPin(String pin) {
        return assertThat(pin)
            .as(() -> "PIN: %s".formatted(pin));
    }
}
