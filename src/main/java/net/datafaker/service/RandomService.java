package net.datafaker.service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class RandomService {
    private static final Random SHARED_RANDOM = ThreadLocalRandom.current();
    private final Random random;

    /**
     * Uses a default shared random.
     */
    public RandomService() {
        this(SHARED_RANDOM);
    }

    /**
     * @param random If null is passed in, a default Random is assigned
     */
    public RandomService(Random random) {
        this.random = random != null ? random : SHARED_RANDOM;
    }

    @SuppressWarnings("unused")
    public int nextInt() {
        return random.nextInt();
    }

    public int nextInt(int n) {
        return random.nextInt(n);
    }

    public int nextInt(int min, int max) {
        if (min == max) return min;
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public IntStream ints() {
        return ThreadLocalRandom.current().ints();
    }

    public IntStream ints(long size) {
        return ThreadLocalRandom.current().ints(size);
    }

    public IntStream ints(long size, int min, int max) {
        return ThreadLocalRandom.current().ints(size, min, max);
    }

    public IntStream ints(int min, int max) {
        return ThreadLocalRandom.current().ints(min, max);
    }

    @SuppressWarnings("unused")
    public float nextFloat() {
        return random.nextFloat();
    }

    public long nextLong() {
        return random.nextLong();
    }

    public long nextLong(long n) {
        return ThreadLocalRandom.current().nextLong(n);
    }

    public long nextLong(long min, long max) {
        if (min == max) return min;
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    public LongStream longs() {
        return ThreadLocalRandom.current().longs();
    }

    public LongStream longs(long size) {
        return ThreadLocalRandom.current().longs(size);
    }

    public LongStream longs(long size, long min, long max) {
        return ThreadLocalRandom.current().longs(size, min, max);
    }

    public LongStream longs(long min, long max) {
        return ThreadLocalRandom.current().longs(min, max);
    }

    public double nextDouble() {
        return random.nextDouble();
    }

    public double nextDouble(double min, double max) {
        if (min == max) return min;
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public DoubleStream doubles() {
        return ThreadLocalRandom.current().doubles();
    }

    public DoubleStream doubles(long size) {
        return ThreadLocalRandom.current().doubles(size);
    }

    public DoubleStream doubles(long size, double min, double max) {
        return ThreadLocalRandom.current().doubles(size, min, max);
    }

    public DoubleStream doubles(double min, double max) {
        return ThreadLocalRandom.current().doubles(min, max);
    }

    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    public String hex() {
        return hex(8);
    }

    public String hex(int length) {
        if (length <= 0) {
            return ""; // Keep the existing behavior instead of throwing an error.
        }
        final char[] hexChars = new char[length];
        for (int i = 0; i < length; i++) {
            final int nextHex = nextInt(16);
            if (nextHex < 10) {
                hexChars[i] = (char) ('0' + nextHex);
            } else {
                hexChars[i] = (char) ('A' + nextHex - 10);
            }
        }
        return new String(hexChars);
    }

    public Random getRandomInternal() {
        return random;
    }
}
