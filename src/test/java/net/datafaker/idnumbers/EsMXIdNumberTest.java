package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.IdNumber;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Locale;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class EsMXIdNumberTest {

    private static final Pattern REGEX = Pattern.compile(
        "[A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z,0-9]\\d");
    private static final BaseFaker ES_MX_FAKER = new BaseFaker(new Locale("es-MX"));

    @RepeatedTest(100)
    void testValidMXSsn() {
        IdNumber idNumber = ES_MX_FAKER.idNumber();
        assertThat(idNumber.valid()).matches(REGEX);
        assertThat(idNumber.invalid()).matches(REGEX);
    }

    @RepeatedTest(100)
    void testInvalidMXSsn() {
        final IdNumber idNumber = ES_MX_FAKER.idNumber();
        assertThat(idNumber.validEsMXSsn()).matches(REGEX);
        assertThat(idNumber.invalidEsMXSsn()).matches(REGEX);
    }
}
