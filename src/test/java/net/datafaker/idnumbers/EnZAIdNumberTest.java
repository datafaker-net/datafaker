package net.datafaker.idnumbers;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/*
 * This file was used to test the issue #566 by SE_CHWJ
 */
public class EnZAIdNumberTest {

    @Test
    public void testExistSsn() {
        EnZAIdNumber idNumber = new EnZAIdNumber();

        assertThat(idNumber.validSsn("9202204720085"), is(false));
        assertThat(idNumber.validSsn("foo2204720082"), is(false));
        assertThat(idNumber.validSsn("9232454720082"), is(false));

        assertThat(idNumber.validSsn("9202204720083"), is(true));
        assertThat(idNumber.validSsn("8801235111088"), is(true));
    }

    @RepeatedTest(100)
    public void testFakerSsn() {
        EnZAIdNumber idNumber = new EnZAIdNumber();
        final Faker f = new Faker(new Locale("en-ZA"));
        assertThat(idNumber.validSsn(f.idNumber().valid()), is(true));
        assertThat(idNumber.validSsn(f.idNumber().invalid()), is(false));
    }

    @RepeatedTest(100)
    public void testSsnFormat() {
        final Faker f = new Faker(new Locale("en-ZA"));
        assertThat(f.idNumber().valid(), matchesRegularExpression("\\d{10}[01][8]\\d{1}"));
        assertThat(f.idNumber().invalid(), matchesRegularExpression("\\d{10}[01][8]\\d{1}"));
    }
}
