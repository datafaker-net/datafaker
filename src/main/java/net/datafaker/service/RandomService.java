package net.datafaker.service;

import java.util.Random;

public class RandomService {
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

    public int nextInt() {
        return random.nextInt();
    }

    public int nextInt(int n) {
        return random.nextInt(n);
    }

    public Integer nextInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

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

    /**
     * Gaussian or normal distribution.
     * @see <a href="https://en.wikipedia.org/wiki/Normal_distribution">Normal distribution</a>
     * ~ 68 % of generated values will belong to the segment [mean - deviation, mean + deviation]
     * ~ 95 % of generated values will belong to the segment [mean - 2 * deviation, mean + 2 * deviation]
     * ~ 99 % of generated values will belong to the segment [mean - 3 * deviation, mean + 3 * deviation]
     *
     * @return Returns the next pseudorandom, Gaussian ("normally") distributed double value with mean 0.0
     * and standard deviation 1.0 from this random number generator's sequence.
     */
    public double nextGaussian() {
        return random.nextGaussian();
    }

    /**
     * Gaussian or normal distribution with predefined mean and standard deviation.
     * @see <a href="https://en.wikipedia.org/wiki/Normal_distribution">Normal distribution</a>
     * ~ 68 % of generated values will belong to the segment [mean - deviation, mean + deviation]
     * ~ 95 % of generated values will belong to the segment [mean - 2 * deviation, mean + 2 * deviation]
     * ~ 99 % of generated values will belong to the segment [mean - 3 * deviation, mean + 3 * deviation]
     *
     * @param mean      the mean
     * @param deviation the standard deviation
     * @return Returns the next pseudorandom, Gaussian ("normally") distributed double value with mean {@code mean}
     * and standard deviation {@code deviation} from this random number generator's sequence.
     */
    public double nextGaussian(double mean, double deviation) {
        return mean + deviation * nextGaussian();
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
