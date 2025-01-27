package net.datafaker.idnumbers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static net.datafaker.idnumbers.LatvianIdNumber.centuryDigit;
import static org.assertj.core.api.Assertions.assertThat;

class LatvianIdNumberTest {
    @Test
    void checksum() {
        assertThat(LatvianIdNumber.checksum("080594-1684")).isEqualTo(1);
        assertThat(LatvianIdNumber.checksum("121282-1121")).isEqualTo(0);
        assertThat(LatvianIdNumber.checksum("121282-8888")).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(ints = {1800, 1801, 1802, 1888, 1898, 1899})
    void centuryDigit_18xx(int year) {
        assertThat(centuryDigit(year)).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(ints = {1900, 1901, 1902, 1988, 1998, 1999})
    void centuryDigit_19xx(int year) {
        assertThat(centuryDigit(year)).isEqualTo(1);
    }

    @ParameterizedTest
    @ValueSource(ints = {2000, 2001, 2002, 2088, 2098, 2099})
    void centuryDigit_20xx(int year) {
        assertThat(centuryDigit(year)).isEqualTo(2);
    }
}
