package net.datafaker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.shuffle;

/**
 * Implementation of {@link java.util.random.RandomGenerator}
 * that guarantees an evenly distributed set of values in a random order.
 *
 * Good for using in tests instead of just {@link java.util.Random}
 */
public class EvenlyDistributedRandomGenerator extends Random {
    private final List<Double> values;
    private int index;

    /**
     * Create a Random instance that splits [lower, upper) to N equal intervals,
     * picks a single value from each interval, and returns them in random order.
     */
    public EvenlyDistributedRandomGenerator(int lowerInclusive, int upperExclusive, int count) {
        this.values = fillEvenly(lowerInclusive, upperExclusive, count);
        shuffle(values);
    }

    private static List<Double> fillEvenly(int lowerInclusive, int upperExclusive, int count) {
        List<Double> values = new ArrayList<>(count);

        Random random = new Random();
        double step = 1.0d * (upperExclusive - lowerInclusive) / count;
        for (int i = 0; i < count; i++) {
            values.add(lowerInclusive + i * step + random.nextDouble(step));
        }
        return values;
    }

    @Override
    public double nextDouble() {
        return values.get(index++);
    }

    @Override
    public int nextInt(int bound) {
        return (int) values.get(index++).doubleValue();
    }
}
