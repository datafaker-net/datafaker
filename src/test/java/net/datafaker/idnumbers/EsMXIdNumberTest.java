package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseFaker;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class EsMXIdNumberTest {

    @RepeatedTest(100)
    void testValidMXSsn() {
        final BaseFaker f = new BaseFaker(new Locale("es-MX"));
        assertThat(f.idNumber().valid()).matches("[A-Z][A-Z][A-Z][A-Z]\\d{6}[HM]" +
            "[A-Z][A-Z][A-Z][A-Z][A-Z][A-Z,0-9]\\d");
        assertThat(f.idNumber().invalid()).matches("[A-Z][A-Z][A-Z][A-Z]\\d{6}[HM]" +
            "[A-Z][A-Z][A-Z][A-Z][A-Z][A-Z,0-9]\\d");
    }

    @RepeatedTest(100)
    void testInvalidMXSsn() {
        final BaseFaker f = new BaseFaker(new Locale("es-MX"));
        assertThat(f.idNumber().validEsMXSsn()).matches("[A-Z][A-Z][A-Z][A-Z]\\d{6}[HM]" +
            "[A-Z][A-Z][A-Z][A-Z][A-Z][A-Z,0-9]\\d");
        assertThat(f.idNumber().invalidEsMXSsn()).matches("[A-Z][A-Z][A-Z][A-Z]\\d{6}[HM]" +
            "[A-Z][A-Z][A-Z][A-Z][A-Z][A-Z,0-9]\\d");
    }
}
