package net.datafaker.idnumbers;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * This file was used to test the issue #566 by SE_CHWJ
 */
public class EnZAIdNumberTest {

    @Test
    public void testExistSsn() {
        EnZAIdNumber idNumber = new EnZAIdNumber();

        assertFalse(idNumber.validSsn("9202204720085"));
        assertFalse(idNumber.validSsn("foo2204720082"));
        assertFalse(idNumber.validSsn("9232454720082"));

        assertTrue(idNumber.validSsn("9202204720083"));
        assertTrue(idNumber.validSsn("8801235111088"));
    }

    @RepeatedTest(100)
    public void testFakerSsn() {
        EnZAIdNumber idNumber = new EnZAIdNumber();
        final Faker f = new Faker(new Locale("en-ZA"));
        assertTrue(idNumber.validSsn(f.idNumber().valid()));
        assertFalse(idNumber.validSsn(f.idNumber().invalid()));
    }

    @RepeatedTest(100)
    public void testSsnFormat() {
        final Faker f = new Faker(new Locale("en-ZA"));
        assertThat(f.idNumber().valid()).matches("\\d{10}[01][8]\\d");
        assertThat(f.idNumber().invalid()).matches("\\d{10}[01][8]\\d");
    }
}
