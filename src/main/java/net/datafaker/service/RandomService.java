package net.datafaker.service;

import java.util.List;
import java.util.Map;
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
        return random.nextInt(min, max + 1);
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
            throw new IllegalArgumentException("bound must be positive: " + n);
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

    /**
     * Returns a weighted random element from the given list, where each element is represented as a Map.Entry
     * with the key being the weight (Double) and the value being the actual element (T).
     *
     * @param items List of entries where each entry has a weight and a corresponding value.
     * @param <T> The type of the element to pick from.
     * @return A randomly selected element based on its weight.
     * @throws IllegalArgumentException if the list is null, empty, or contains non-positive weights.
     */
    public <T> T weightedArrayElement(List<Map.Entry<Double, T>> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("weightedArrayElement expects a non-empty list");
        }

        double totalWeight = 0.0;
        for (Map.Entry<Double, T> item : items) {
            if (item.getKey() <= 0) {
                throw new IllegalArgumentException("All weights must be positive numbers");
            }
            totalWeight += item.getKey();
        }

        double randomValue = random.nextDouble() * totalWeight;
        double currentWeight = 0.0;

        for (Map.Entry<Double, T> item : items) {
            currentWeight += item.getKey();
            if (randomValue < currentWeight) {
                return item.getValue();
            }
        }

        // Return the last element in case of rounding errors
        return items.get(items.size() - 1).getValue();
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

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + random;
    }
}
