package net.datafaker.service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

    public double nextDouble() {
        return random.nextDouble();
    }

    public double nextDouble(double min, double max) {
        if (min == max) return min;
        return ThreadLocalRandom.current().nextDouble(min, max);
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
