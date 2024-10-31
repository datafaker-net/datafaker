package net.datafaker.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RangeTest {
    @Test
    void inclusive_minShouldBeLessThanMax() {
        assertThatThrownBy(() -> Range.inclusive(45, 44)).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> Range.inclusive(45, 45)).doesNotThrowAnyException();
    }

    @Test
    void exclusive_minShouldBeLessThanMaxMinusOne() {
        assertThatThrownBy(() -> Range.exclusive(44, 45))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Lower bound (44) >= upper bound-1 (44)");
        assertThatThrownBy(() -> Range.exclusive(Integer.MIN_VALUE, Integer.MIN_VALUE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Lower bound (-2147483648) >= upper bound-1 (-2147483649)");
        assertThatThrownBy(() -> Range.exclusive(Long.MAX_VALUE, Long.MAX_VALUE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Lower bound (9223372036854775807) >= upper bound-1 (9223372036854775806)");
        assertThatThrownBy(() -> Range.exclusive(Long.MIN_VALUE, Long.MIN_VALUE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Lower bound (-9223372036854775808) >= upper bound (-9223372036854775808)");
        assertThatThrownBy(() -> Range.exclusive(Long.MAX_VALUE, Long.MIN_VALUE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Lower bound (9223372036854775807) >= upper bound (-9223372036854775808)");
        assertThatCode(() -> Range.exclusive(44, 46)).doesNotThrowAnyException();
    }

    @Test
    void inclusiveExclusive_minShouldBeLessThanMax() {
        assertThatThrownBy(() -> Range.inclusiveExclusive(44, 44)).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> Range.inclusiveExclusive(44, 45)).doesNotThrowAnyException();
    }

    @Test
    void exclusiveInclusive_minShouldBeLessThanMax() {
        assertThatThrownBy(() -> Range.exclusiveInclusive(44, 44)).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> Range.exclusiveInclusive(44, 45)).doesNotThrowAnyException();
    }
}
