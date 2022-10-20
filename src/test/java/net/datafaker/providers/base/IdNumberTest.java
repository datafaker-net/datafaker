package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class IdNumberTest extends BaseFakerTest<BaseFaker> {

    @Test
    void valid() {
        assertThat(faker.idNumber().valid()).matches("[0-8]\\d{2}-\\d{2}-\\d{4}");
    }

    @Test
    void invalid() {
        assertThat(faker.idNumber().invalid()).matches("[0-9]\\d{2}-\\d{2}-\\d{4}");
    }

    @RepeatedTest(100)
    void ssnValid() {
        assertThat(faker.idNumber().ssnValid()).matches("[0-8]\\d{2}-\\d{2}-\\d{4}");
    }

    @RepeatedTest(100)
    void validSwedishSsn() {
        final BaseFaker f = new BaseFaker(new Locale("sv_SE"));
        assertThat(f.idNumber().validSvSeSsn()).matches("\\d{6}[-+]\\d{4}");
    }

    @RepeatedTest(100)
    void invalidSwedishSsn() {
        final BaseFaker f = new BaseFaker(new Locale("sv_SE"));
        assertThat(f.idNumber().invalidSvSeSsn()).matches("\\d{6}[-+]\\d{4}");
    }

    @RepeatedTest(100)
    void validEnZaSsn() {
        final BaseFaker f = new BaseFaker(new Locale("en_ZA"));
        assertThat(f.idNumber().validEnZaSsn()).matches("[0-9]{10}([01])8[0-9]");
    }

    @RepeatedTest(100)
    void invalidEnZaSsn() {
        final BaseFaker f = new BaseFaker(new Locale("en_ZA"));
        assertThat(f.idNumber().inValidEnZaSsn()).matches("[0-9]{10}([01])8[0-9]");
    }

    @RepeatedTest(100)
    void singaporeanFin() {
        assertThat(faker.idNumber().singaporeanFin()).matches("G[0-9]{7}[A-Z]");
    }

    @RepeatedTest(100)
    void singaporeanFinBefore2000() {
        assertThat(faker.idNumber().singaporeanFinBefore2000()).matches("F[0-9]{7}[A-Z]");
    }

    @RepeatedTest(100)
    void singaporeanUin() {
        assertThat(faker.idNumber().singaporeanUin()).matches("T[0-9]{7}[A-Z]");
    }

    @RepeatedTest(100)
    void singaporeanUinBefore2000() {
        assertThat(faker.idNumber().singaporeanUinBefore2000()).matches("S[0-9]{7}[A-Z]");
    }

    @RepeatedTest(100)
    void peselNumber() {
        assertThat(faker.idNumber().peselNumber()).matches("[0-9]{11}");
    }
}
