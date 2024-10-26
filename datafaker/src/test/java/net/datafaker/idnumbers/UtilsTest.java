package net.datafaker.idnumbers;

import org.junit.jupiter.api.Test;

import static net.datafaker.idnumbers.Utils.digit;
import static net.datafaker.idnumbers.Utils.digitAt;
import static net.datafaker.idnumbers.Utils.multiply;
import static org.assertj.core.api.Assertions.assertThat;

class UtilsTest {
    @Test
    void digit_parsesGivenCharToNumber() {
        assertThat(digit('0')).isEqualTo(0);
        assertThat(digit('1')).isEqualTo(1);
        assertThat(digit('2')).isEqualTo(2);
        assertThat(digit('8')).isEqualTo(8);
        assertThat(digit('9')).isEqualTo(9);
    }

    @Test
    void digitAt_parsesGivenCharToNumber() {
        assertThat(digitAt("12345", 0)).isEqualTo(1);
        assertThat(digitAt("12345", 1)).isEqualTo(2);
        assertThat(digitAt("12345", 2)).isEqualTo(3);
        assertThat(digitAt("12345", 3)).isEqualTo(4);
        assertThat(digitAt("12345", 4)).isEqualTo(5);
    }

    @Test
    void multiply_digits() {
        assertThat(multiply("1", new int[]{1})).isEqualTo(1);
        assertThat(multiply("1", new int[]{2})).isEqualTo(2);
        assertThat(multiply("23", new int[]{4, 5})).isEqualTo(2 * 4 + 3 * 5);
    }
}
