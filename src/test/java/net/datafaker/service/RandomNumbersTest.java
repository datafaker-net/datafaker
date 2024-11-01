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
        assertThat(all(() -> randomService.nextLong(Range.exclusiveInclusive(2L, 5L)))).containsExactly(3L, 4L, 5L);
    }

    @Test
    void range_exclusive() {
        assertThat(all(() -> randomService.nextLong(Range.exclusive(2L, 5L)))).containsExactly(3L, 4L);
        assertThat(all(() -> randomService.nextInt(Range.exclusive(2, 5)))).containsExactly(3, 4);
    }

    @RepeatedTest(100)
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
