package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class IdNumberTest extends AbstractFakerTest {

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
        final Faker f = new Faker(new Locale("sv_SE"));
        assertThat(f.idNumber().validSvSeSsn()).matches("\\d{6}[-+]\\d{4}");
    }

    @RepeatedTest(100)
    void testInvalidSwedishSsn() {
        final Faker f = new Faker(new Locale("sv_SE"));
        assertThat(f.idNumber().invalidSvSeSsn()).matches("\\d{6}[-+]\\d{4}");
    }

    @RepeatedTest(100)
    void testValidEnZaSsn() {
        final Faker f = new Faker(new Locale("en_ZA"));
        assertThat(f.idNumber().validEnZaSsn()).matches("[0-9]{10}([01])8[0-9]");
    }

    @RepeatedTest(100)
    void testInvalidEnZaSsn() {
        final Faker f = new Faker(new Locale("en_ZA"));
        assertThat(f.idNumber().inValidEnZaSsn()).matches("[0-9]{10}([01])8[0-9]");
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
}
