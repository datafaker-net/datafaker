package net.datafaker.service;

import java.util.Random;

public class RandomService {    
    private final Random random = new Random();
    private final long seed;

    /**
     * Uses a default shared random.
     */
    public RandomService() {
        seed = random.nextLong();
        random.setSeed(seed);
    }

    public RandomService(long seed) {
        this.seed = seed;
        random.setSeed(seed);
    }

    @SuppressWarnings("unused")
    public int nextInt() {
        return random.nextInt();
    }

    public int nextInt(int n) {
        return random.nextInt(n);
    }

    public Integer nextInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    @SuppressWarnings("unused")
    public float nextFloat() {
        return random.nextFloat();
    }

    public long nextLong() {
        return random.nextLong();
    }

    // lifted from http://stackoverflow.com/questions/2546078/java-random-long-number-in-0-x-n-range
    public long nextLong(long n) {
        if (n <= 0) {
            throw new IllegalArgumentException("bound must be positive");
        }

        long bits, val;
        do {
            long randomLong = random.nextLong();
            bits = (randomLong << 1) >>> 1;
            val = bits % n;
        } while (bits - val + (n - 1) < 0L);
        return val;
    }

    public long nextLong(long min, long max) {
        return min + (long) (nextDouble() * (max - min));
    }

    public double nextDouble() {
        return random.nextDouble();
    }

    public double nextDouble(double min, double max) {
        return min + (nextDouble() * (max - min));
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

    /**
     * getSeed
     * 
     * @return returns the seed used by the pseudo-random generator associated with this RandomService
     */
    public long getSeed(){
        return seed;
    }
}
