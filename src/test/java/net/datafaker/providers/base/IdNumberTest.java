package net.datafaker.providers.base;

import net.datafaker.Faker;
import net.datafaker.idnumbers.EnZAIdNumber;
import net.datafaker.idnumbers.SvSEIdNumber;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;

class IdNumberTest extends BaseFakerTest<BaseFaker> {

    public static final IdNumber SV_SE_ID_NUMBER = new BaseFaker(new Locale("sv_SE")).idNumber();
    public static final Pattern SV_SE_ID_NUMBER_PATTERN = Pattern.compile("\\d{6}[-+]\\d{4}");
    public static final IdNumber EN_ZA_OD_NUMBER= new BaseFaker(new Locale("en_ZA")).idNumber();
    public static final Pattern EN_ZA_ID_NUMBER_PATTERN = Pattern.compile("[0-9]{10}([01])8[0-9]");

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
    }

    @RepeatedTest(100)
    void testValidSwedishSsn() {
        final String actual = SV_SE_ID_NUMBER.validSvSeSsn();
        assertThat(actual).matches(SV_SE_ID_NUMBER_PATTERN);
        assertThat(SvSEIdNumber.isValidSwedishSsn(actual)).isTrue();
    }

    @RepeatedTest(100)
    void testInvalidSwedishSsn() {
        assertThat(SV_SE_ID_NUMBER.invalidSvSeSsn()).matches(SV_SE_ID_NUMBER_PATTERN);
    }

    @RepeatedTest(100)
    void testValidEnZaSsn() {
        String actual = EN_ZA_OD_NUMBER.validEnZaSsn();
        assertThat(actual).matches(EN_ZA_ID_NUMBER_PATTERN);
        assertThat(EnZAIdNumber.isValidEnZASsn(actual)).isTrue();
    }

    @RepeatedTest(100)
    void testInvalidEnZaSsn() {
        assertThat(EN_ZA_OD_NUMBER.inValidEnZaSsn()).matches(EN_ZA_ID_NUMBER_PATTERN);
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
        Faker faker = new Faker(new Locale("et", "EE"));
        assertThat(faker.idNumber().valid()).matches("[1-6][0-9]{10}");
    }

    @RepeatedTest(100)
    void estonianPersonalCode_invalid() {
        Faker faker = new Faker(new Locale("et", "EE"));
        assertThat(faker.idNumber().invalid()).matches("[1-6][0-9]{10}");
    }

    @RepeatedTest(100)
    void albanianPersonalCode_valid() {
        Faker faker = new Faker(new Locale("sq", "AL"));
        String pin = faker.idNumber().valid();
        assertThat(pin.length()).isEqualTo(10);
        assertThat(parseInt(pin.substring(2, 4)) % 50)
            .as(() -> "Valid PIN %s should have month number between 1..12 (for males) or 51..62 (for females)".formatted(pin))
            .isBetween(1, 12);
    }

    @RepeatedTest(100)
    void albanianPersonalCode_invalid() {
        Faker faker = new Faker(new Locale("sq", "AL"));
        String pin = faker.idNumber().invalid();
        assertThat(pin.length()).isEqualTo(10);
        assertThat(parseInt(pin.substring(2, 4)))
            .as(() -> "Invalid PIN %s should have month greater than (any month + 50)".formatted(pin))
            .isGreaterThan(62);
    }

    @RepeatedTest(100)
    void moldovaPersonalCode_valid() {
        Faker faker = new Faker(new Locale("ro", "MD"));
        String pin = faker.idNumber().valid();
        assertThat(pin.length())
            .as(() -> "Presumably valid PIN: '%s'".formatted(pin))
            .isEqualTo(13);
    }

    @RepeatedTest(100)
    void moldovaPersonalCode_invalid() {
        Faker faker = new Faker(new Locale("ro", "MD"));
        String pin = faker.idNumber().invalid();
        assertThat(pin.length())
            .as(() -> "Presumably invalid PIN: '%s'".formatted(pin))
            .isEqualTo(13);
    }
}
