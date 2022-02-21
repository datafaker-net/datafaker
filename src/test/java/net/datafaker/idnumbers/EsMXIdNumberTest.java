package net.datafaker.idnumbers;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Locale;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class EsMXIdNumberTest {

    @RepeatedTest(100)
    public void testValidMXSsn() {
        final Faker f = new Faker(new Locale("es-MX"));
        assertThat(f.idNumber().valid(), matchesRegularExpression("[A-Z][A-Z][A-Z][A-Z]\\d{6}[HM]" +
            "[A-Z][A-Z][A-Z][A-Z][A-Z][A-Z,0-9]\\d{1}"));
        assertThat(f.idNumber().invalid(), matchesRegularExpression("[A-Z][A-Z][A-Z][A-Z]\\d{6}[HM]" +
            "[A-Z][A-Z][A-Z][A-Z][A-Z][A-Z,0-9]\\d{1}"));
    }

    @RepeatedTest(100)
    public void testInvalidMXSsn() {
        final Faker f = new Faker(new Locale("es-MX"));
        assertThat(f.idNumber().validEsMXSsn(), matchesRegularExpression("[A-Z][A-Z][A-Z][A-Z]\\d{6}[HM]" +
            "[A-Z][A-Z][A-Z][A-Z][A-Z][A-Z,0-9]\\d{1}"));
        assertThat(f.idNumber().invalidEsMXSsn(), matchesRegularExpression("[A-Z][A-Z][A-Z][A-Z]\\d{6}[HM]" +
            "[A-Z][A-Z][A-Z][A-Z][A-Z][A-Z,0-9]\\d{1}"));
    }
}
