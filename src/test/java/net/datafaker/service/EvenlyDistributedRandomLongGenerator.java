package net.datafaker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.shuffle;

/**
 * Implementation of {@link java.util.random.RandomGenerator}
 * that guarantees an evenly distributed set of values in a random order.
 *
 * Good for using in tests instead of just {@link Random}
 */
public class EvenlyDistributedRandomLongGenerator extends Random {
    private final List<Long> values;
    private final int count;
    private final long lowerInclusive;
    private final long upperExclusive;
    private int index;

    /**
     * Create a Random instance that returns all values between [lower...upper) in random order.
     */
    public EvenlyDistributedRandomLongGenerator(long lowerInclusive, long upperExclusive) {
        if (lowerInclusive >= upperExclusive) {
            throw new IllegalArgumentException("Expected %s < %s".formatted(lowerInclusive, upperExclusive));
        }
        this.lowerInclusive = lowerInclusive;
        this.upperExclusive = upperExclusive;
        this.count = Math.toIntExact(upperExclusive - lowerInclusive);
        this.values = fillEvenly(lowerInclusive, upperExclusive, count);
        shuffle(values);
    }

    private static List<Long> fillEvenly(long lowerInclusive, long upperExclusive, int count) {
        List<Long> values = new ArrayList<>(count);
        for (long i = lowerInclusive; i < upperExclusive; i++) {
            values.add(i);
        }
        return values;
    }

    @Override
    public double nextDouble() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int nextInt(int bound) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long nextLong() {
        int i = index;
        index = (index + 1) % count;
        return values.get(i);
    }

    @Override
    public long nextLong(long origin, long bound) {
        if (origin != lowerInclusive || bound != upperExclusive) {
            throw new IllegalArgumentException("Only numbers within [%s, %s) allowed, but requested: [%s, %s]".formatted(lowerInclusive, upperExclusive, origin, bound));
        }
        return nextLong();
    }

    @Override
    public long nextLong(long bound) {
        return nextLong(0, bound);
    }
}
