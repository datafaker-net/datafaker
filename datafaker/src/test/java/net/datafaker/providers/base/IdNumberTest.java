package net.datafaker.providers.base;

import net.datafaker.Faker;
import net.datafaker.idnumbers.SouthAfricanIdNumber;
import net.datafaker.idnumbers.SwedenIdNumber;
import org.assertj.core.api.AbstractStringAssert;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;

class IdNumberTest extends BaseFakerTest<BaseFaker> {

    private static final Faker SWEDISH = new Faker(new Locale("sv", "SE"));
    private static final Faker SOUTH_AFRICA = new Faker(new Locale("en", "ZA"));
    private static final Faker US = new Faker(new Locale("en", "US"));
    private static final Faker ESTONIAN = new Faker(new Locale("et", "EE"));
    private static final Faker ALBANIAN = new Faker(new Locale("sq", "AL"));
    private static final Faker BULGARIAN = new Faker(new Locale("bg", "BG"));
    private static final Faker MACEDONIAN = new Faker(new Locale("mk", "MK"));
    private static final Faker ROMANIAN = new Faker(new Locale("ro", "RO"));
    private static final Faker UKRAINIAN = new Faker(new Locale("uk", "UA"));

    private static final Pattern SWEDISH_ID_NUMBER_PATTERN = Pattern.compile("\\d{6}[-+]\\d{4}");
    private static final Pattern UKRAINIAN_UNZR_PATTERN = Pattern.compile("\\d{8}-\\d{5}");
    private static final Pattern SOUTH_AFRICA_ID_NUMBER_PATTERN = Pattern.compile("[0-9]{10}([01])8[0-9]");

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
        assertThat(actual).matches(SWEDISH_ID_NUMBER_PATTERN);
        assertThat(SwedenIdNumber.isValidSwedishSsn(actual)).isTrue();
    }

    @RepeatedTest(100)
    void testInvalidSwedishSsn() {
        String actual = SWEDISH.idNumber().invalid();
        assertThat(actual).matches(SWEDISH_ID_NUMBER_PATTERN);
        assertThat(SwedenIdNumber.isValidSwedishSsn(actual)).isFalse();
    }

    @RepeatedTest(100)
    void southAfrica_valid() {
        String actual = SOUTH_AFRICA.idNumber().valid();
        assertThat(actual).matches(SOUTH_AFRICA_ID_NUMBER_PATTERN);
        assertThat(SouthAfricanIdNumber.isValidEnZASsn(actual)).isTrue();
    }

    @RepeatedTest(100)
    void southAfrica_invalid() {
        assertThat(SOUTH_AFRICA.idNumber().invalid()).matches(SOUTH_AFRICA_ID_NUMBER_PATTERN);
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
    void testPeselNumber() {
        assertThat(faker.idNumber().peselNumber()).matches("[0-9]{11}");
    }

    @RepeatedTest(100)
    void estonianPersonalCode_valid() {
        assertThatPin(ESTONIAN.idNumber().valid()).matches("[1-6][0-9]{10}");
    }

    @RepeatedTest(100)
    void estonianPersonalCode_invalid() {
        assertThatPin(ESTONIAN.idNumber().invalid()).matches("[1-6][0-9]{10}");
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
        assertThatPin(UKRAINIAN.idNumber().valid()).matches(UKRAINIAN_UNZR_PATTERN);
    }

    @RepeatedTest(100)
    void ukrainianUznr_invalid() {
        assertThatPin(UKRAINIAN.idNumber().invalid()).matches(UKRAINIAN_UNZR_PATTERN);
    }

    private static AbstractStringAssert<?> assertThatPin(String pin) {
        return assertThat(pin)
            .as(() -> "PIN: %s".formatted(pin));
    }
}
