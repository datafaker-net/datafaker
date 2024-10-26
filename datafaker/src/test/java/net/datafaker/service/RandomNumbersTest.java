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
        assertThat(all(() -> randomService.nextLong(3))).containsExactly(0L, 1L, 2L);
        assertThat(all(() -> randomService.nextLong(2, 4))).containsExactly(2L, 3L);
        assertThat(all(() -> randomService.nextInt(3))).containsExactly(0, 1, 2);

        // inclusive: see https://github.com/datafaker-net/datafaker/issues/1395
        assertThat(all(() -> randomService.nextInt(2, 4))).containsExactly(2, 3, 4);
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
