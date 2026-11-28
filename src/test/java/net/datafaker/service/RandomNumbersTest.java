package net.datafaker.service;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

class RandomNumbersTest {
    private final RandomService randomService = new RandomService();

    @Test
    void nextInt_minInclusive_maxExclusive() {
        assertThat(all(() -> randomService.nextInt(3))).containsExactly(0, 1, 2);
        assertThat(all(() -> randomService.nextInt(2, 4))).containsExactly(2, 3, 4); // legacy
        assertThat(all(() -> randomService.nextLong(3))).containsExactly(0L, 1L, 2L);
        assertThat(all(() -> randomService.nextLong(2, 4))).containsExactly(2L, 3L);
    }

    @Test
    void range_inclusive() {
        assertThat(all(() -> randomService.nextInt(Range.inclusive(2, 4)))).containsExactly(2, 3, 4);
        assertThat(all(() -> randomService.nextLong(Range.inclusive(2L, 4L)))).containsExactly(2L, 3L, 4L);
    }

    @Test
    void range_inclusive_exclusive() {
        assertThat(all(() -> randomService.nextInt(Range.inclusiveExclusive(2, 5)))).containsExactly(2, 3, 4);
        assertThat(all(() -> randomService.nextLong(Range.inclusiveExclusive(2L, 5L)))).containsExactly(2L, 3L, 4L);
    }

    @Test
    void range_exclusive_inclusive() {
        assertThat(all(() -> randomService.nextInt(Range.exclusiveInclusive(2, 5)))).containsExactly(3, 4, 5);
        assertThat(randomService.nextInt(Range.exclusiveInclusive(Integer.MIN_VALUE, Integer.MIN_VALUE + 1))).isEqualTo(Integer.MIN_VALUE + 1);

        assertThat(all(() -> randomService.nextLong(Range.exclusiveInclusive(2L, 5L)))).containsExactly(3L, 4L, 5L);
        assertThat(randomService.nextLong(Range.exclusiveInclusive(Long.MIN_VALUE, Long.MIN_VALUE + 1))).isEqualTo(Long.MIN_VALUE + 1);
        assertThat(randomService.nextLong(Range.exclusiveInclusive(Long.MAX_VALUE - 2, Long.MAX_VALUE))).isEqualTo(Long.MAX_VALUE - 1);
    }

    @Test
    void range_exclusive() {
        assertThat(all(() -> randomService.nextLong(Range.exclusive(2L, 5L)))).containsExactly(3L, 4L);
        assertThat(randomService.nextInt(Range.exclusive(Integer.MIN_VALUE, Integer.MIN_VALUE + 2))).isEqualTo(Integer.MIN_VALUE + 1);

        assertThat(all(() -> randomService.nextInt(Range.exclusive(2, 5)))).containsExactly(3, 4);
        assertThat(randomService.nextLong(Range.exclusive(Long.MIN_VALUE, Long.MIN_VALUE + 2))).isEqualTo(Long.MIN_VALUE + 1);
        assertThat(randomService.nextLong(Range.exclusive(Long.MAX_VALUE - 2, Long.MAX_VALUE))).isEqualTo(Long.MAX_VALUE - 1);
    }

    @RepeatedTest(10)
    void nextDouble() {
        assertThat(randomService.nextDouble(2, 3)).isGreaterThanOrEqualTo(2);
        assertThat(randomService.nextDouble(2, 3)).isStrictlyBetween(1.9999, 3.0);
    }

    private <T extends Number & Comparable<T>> SortedSet<T> all(Supplier<T> lambda) {
        SortedSet<T> result = new TreeSet<>();
        for (int i = 0; i < 1000; i++) {
            result.add(lambda.get());
        }
        return result;
    }
}
