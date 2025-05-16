package net.datafaker.idnumbers;

import org.junit.jupiter.api.Test;

import static net.datafaker.providers.base.PersonIdNumber.Gender.FEMALE;
import static net.datafaker.providers.base.PersonIdNumber.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

class HungarianIdNumberTest {

    @Test
    void firstDigit() {
        assertThat(HungarianIdNumber.firstDigit(1899, MALE)).isEqualTo(3);
        assertThat(HungarianIdNumber.firstDigit(1900, MALE)).isEqualTo(1);
        assertThat(HungarianIdNumber.firstDigit(1901, MALE)).isEqualTo(1);
        assertThat(HungarianIdNumber.firstDigit(1899, FEMALE)).isEqualTo(4);
        assertThat(HungarianIdNumber.firstDigit(1900, FEMALE)).isEqualTo(2);
        assertThat(HungarianIdNumber.firstDigit(1901, FEMALE)).isEqualTo(2);
        assertThat(HungarianIdNumber.firstDigit(1999, MALE)).isEqualTo(1);
        assertThat(HungarianIdNumber.firstDigit(2000, MALE)).isEqualTo(3);
        assertThat(HungarianIdNumber.firstDigit(1999, FEMALE)).isEqualTo(2);
        assertThat(HungarianIdNumber.firstDigit(2000, FEMALE)).isEqualTo(4);
    }

    @Test
    void checkDigit() {
        assertThat(HungarianIdNumber.getCheckDigit("807159215")).isEqualTo(3);
        assertThat(HungarianIdNumber.getCheckDigit("100000000")).isEqualTo(1);
        assertThat(HungarianIdNumber.getCheckDigit("010000000")).isEqualTo(2);
        assertThat(HungarianIdNumber.getCheckDigit("001000000")).isEqualTo(3);
        assertThat(HungarianIdNumber.getCheckDigit("000000001")).isEqualTo(9);
        assertThat(HungarianIdNumber.getCheckDigit("170010103")).isEqualTo(10);
    }
}
