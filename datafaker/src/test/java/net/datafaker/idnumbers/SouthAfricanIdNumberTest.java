package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.IdNumber;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static java.lang.Integer.parseInt;
import static net.datafaker.idnumbers.SouthAfricanIdNumber.isValidEnZASsn;
import static net.datafaker.idnumbers.SouthAfricanIdNumber.sequentialNumber;
import static net.datafaker.providers.base.PersonIdNumber.Gender.FEMALE;
import static net.datafaker.providers.base.PersonIdNumber.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * This file was used to test the issue #566 by SE_CHWJ
 */
class SouthAfricanIdNumberTest {

    @Test
    void testExistSsn() {
        assertThat(isValidEnZASsn("9202204720085")).isFalse();
        assertThat(isValidEnZASsn("foo2204720082")).isFalse();
        assertThat(isValidEnZASsn("9232454720082")).isFalse();

        assertThat(isValidEnZASsn("9202204720083")).isTrue();
        assertThat(isValidEnZASsn("8801235111088")).isTrue();
    }

    @RepeatedTest(100)
    void testFakerSsn() {
        final BaseFaker f = new BaseFaker(new Locale("en", "ZA"));
        final IdNumber idNumber = f.idNumber();
        assertThat(isValidEnZASsn(idNumber.valid())).isTrue();
        assertThat(isValidEnZASsn(idNumber.invalid())).isFalse();
    }

    @RepeatedTest(100)
    void testSsnFormat() {
        final BaseFaker f = new BaseFaker(new Locale("en", "ZA"));
        assertThat(f.idNumber().valid()).matches("\\d{10}[01]8\\d");
        assertThat(f.idNumber().invalid()).matches("\\d{10}[01]8\\d");
    }

    @RepeatedTest(100)
    void sequentialNumber_forMales() {
        BaseFaker f = new BaseFaker(new Locale("en", "ZA"));
        String sequentialNumber = sequentialNumber(f, MALE);

        assertThat(sequentialNumber).matches("\\d{4}");
        assertThat(parseInt(sequentialNumber)).isGreaterThanOrEqualTo(5000);
    }

    @RepeatedTest(100)
    void sequentialNumber_forFemales() {
        BaseFaker f = new BaseFaker(new Locale("en", "ZA"));
        String sequentialNumber = sequentialNumber(f, FEMALE);

        assertThat(sequentialNumber).matches("\\d{4}");
        assertThat(parseInt(sequentialNumber)).isLessThan(5000);
    }
}
