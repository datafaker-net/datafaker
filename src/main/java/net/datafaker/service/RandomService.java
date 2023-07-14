package net.datafaker.service;

import java.util.Objects;
import java.util.Random;

public class RandomService {
    private static final char[] HEX_UP = "0123456789ABCDEF".toCharArray();
    private static final char[] HEX_LOWER = "0123456789abcdef".toCharArray();
    private static final Random SHARED_RANDOM = new Random();
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

    public byte[] nextRandomBytes(int numberOfBytes) {
        final byte[] randomBytes = new byte[numberOfBytes];
        random.nextBytes(randomBytes);
        return randomBytes;
    }

    public String hex() {
        return hex(8);
    }

    public String hex(int length) {
        return hex(length, true);
    }

    public String hex(int length, boolean upper) {
        if (length <= 0) {
            return ""; // Keep the existing behavior instead of throwing an error.
        }
        char[] hexArray = upper ? HEX_UP : HEX_LOWER;
        final char[] hexChars = new char[length];
        final byte[] randomBytes = nextRandomBytes(length);
        for (int i = 0; i < length; i++) {
            hexChars[i] = hexArray[((char) randomBytes[i]) % hexArray.length];
        }
        return new String(hexChars);
    }

    public Random getRandomInternal() {
        return random;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RandomService that)) return false;

        return Objects.equals(random, that.random);
    }

    @Override
    public int hashCode() {
        if (random == SHARED_RANDOM) return 1;
        return random != null ? random.hashCode() : 0;
    }
}
