package net.datafaker.idnumbers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static net.datafaker.idnumbers.EstonianIdNumber.firstDigit;
import static net.datafaker.providers.base.PersonIdNumber.Gender.FEMALE;
import static net.datafaker.providers.base.PersonIdNumber.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

class EstonianIdNumberTest {
    /**
     * Samples from <a href="https://et.wikipedia.org/wiki/Isikukood">...</a>
     */
    @Test
    void checksum() {
        assertThat(EstonianIdNumber.checksum("3760503029")).isEqualTo(9);
        assertThat(EstonianIdNumber.checksum("3450123421")).isEqualTo(5);
        assertThat(EstonianIdNumber.checksum("4940313652")).isEqualTo(6);
        assertThat(EstonianIdNumber.checksum("5110712176")).isEqualTo(0);
        assertThat(EstonianIdNumber.checksum("6110712176")).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(ints = {1800, 1801, 1802, 1888, 1898, 1899})
    void firstDigit_18xx(int year) {
        assertThat(firstDigit(year, MALE)).isEqualTo(1);
        assertThat(firstDigit(year, FEMALE)).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(ints = {1900, 1901, 1902, 1988, 1998, 1999})
    void firstDigit_19xx(int year) {
        assertThat(firstDigit(year, MALE)).isEqualTo(3);
        assertThat(firstDigit(year, FEMALE)).isEqualTo(4);
    }

    @ParameterizedTest
    @ValueSource(ints = {2000, 2001, 2002, 2088, 2098, 2099})
    void firstDigit_20xx(int year) {
        assertThat(firstDigit(year, MALE)).isEqualTo(5);
        assertThat(firstDigit(year, FEMALE)).isEqualTo(6);
    }

    @ParameterizedTest
    @ValueSource(ints = {2100, 2101, 2102, 2188, 2198, 2199})
    void firstDigit_21xx(int year) {
        assertThat(firstDigit(year, MALE)).isEqualTo(7);
        assertThat(firstDigit(year, FEMALE)).isEqualTo(8);
    }
}
