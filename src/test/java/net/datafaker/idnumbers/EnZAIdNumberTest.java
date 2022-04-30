package net.datafaker.idnumbers;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * This file was used to test the issue #566 by SE_CHWJ
 */
class EnZAIdNumberTest {

    @Test
    void testExistSsn() {
        EnZAIdNumber idNumber = new EnZAIdNumber();

        assertThat(idNumber.validSsn("9202204720085")).isFalse();
        assertThat(idNumber.validSsn("foo2204720082")).isFalse();
        assertThat(idNumber.validSsn("9232454720082")).isFalse();

        assertThat(idNumber.validSsn("9202204720083")).isTrue();
        assertThat(idNumber.validSsn("8801235111088")).isTrue();
    }

    @RepeatedTest(100)
    void testFakerSsn() {
        EnZAIdNumber idNumber = new EnZAIdNumber();
        final Faker f = new Faker(new Locale("en-ZA"));

        assertThat(idNumber.validSsn(f.idNumber().valid())).isTrue();
        assertThat(idNumber.validSsn(f.idNumber().invalid())).isFalse();
    }

    @RepeatedTest(100)
    void testSsnFormat() {
        final Faker f = new Faker(new Locale("en-ZA"));
        assertThat(f.idNumber().valid()).matches("\\d{10}[01][8]\\d");
        assertThat(f.idNumber().invalid()).matches("\\d{10}[01][8]\\d");
    }
}
