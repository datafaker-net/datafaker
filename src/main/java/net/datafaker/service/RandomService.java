package net.datafaker.service;

import net.datafaker.configuration.ProbabilityConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomService {
    private static final char[] HEX_UP = "0123456789ABCDEF".toCharArray();
    private static final char[] HEX_LOWER = "0123456789abcdef".toCharArray();
    private static final Random SHARED_RANDOM = new Random();
    private final Random random;
    private ProbabilityConfig probabilityConfig = new ProbabilityConfig();

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

    public void setProbabilityConfig(ProbabilityConfig probabilityConfig) {
        this.probabilityConfig = probabilityConfig;
    }

    @SuppressWarnings("unused")
    public int nextInt() {
        return random.nextInt();
    }

    public int nextInt(int n) {
        Map<Object, Double> intProbabilityConfig = probabilityConfig.getProbabilities();
        List<Integer> intKeys = probabilityConfig.getProbabilities()
            .keySet()
            .stream()
            .filter(o -> o instanceof Integer)
            .map(o -> (Integer) o)
            .filter(key -> key < n)
            .collect(Collectors.toList());

        if (intKeys.isEmpty()) {
            return random.nextInt(n);
        }

        double[] probabilities = calculateNumbersProbability(n, intKeys, intProbabilityConfig);

        while (true) {
            double randomDouble = random.nextDouble();
            double probabilitySum = 0.0;
            for (int number = 0; number < probabilities.length; number++) {
                probabilitySum = probabilitySum + probabilities[number];
                if (probabilitySum > randomDouble) {
                    return number;
                }
            }
        }
    }

    private double[] calculateNumbersProbability(int n, List<Integer> intKeys, Map<Object, Double> intProbabilityConfig) {
        double[] probabilities = new double[n];
        Arrays.fill(probabilities, -1);

        int configuredCount = 0;
        double configuredProbabilities = 0.0;
        for (Integer key : intKeys) {
            configuredCount++;
            double probability = intProbabilityConfig.get(key);
            probabilities[key] = probability;
            configuredProbabilities += probability;
        }

        double remainingProbability = (1.0 - configuredProbabilities) / (n - configuredCount);
        for (int i = 0; i < probabilities.length; i++) {
            if (probabilities[i] == -1) {
                probabilities[i] = remainingProbability;
            }
        }

        return probabilities;
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
        Map<Object, Double> probabilities = probabilityConfig.getProbabilities();
        Double trueProbability = probabilities.get(true);
        if (trueProbability != null) {
            return random.nextDouble() < trueProbability;
        }

        Double falseProbability = probabilities.get(false);
        if (falseProbability != null) {
            return random.nextDouble() < (1 - falseProbability);
        }

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
}
