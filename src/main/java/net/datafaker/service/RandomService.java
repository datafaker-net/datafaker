package net.datafaker.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class RandomService {
    private static final String WEIGHT_KEY = "weight";
    private static final String VALUE_KEY = "value";
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

    public int nextInt(int maxExclusive) {
        return random.nextInt(maxExclusive);
    }

    public Integer nextInt(int minInclusive, int maxInclusive) {
        if (minInclusive > maxInclusive)
            throw new IllegalArgumentException("Min (%s) > Max (%s)".formatted(minInclusive, maxInclusive));
        if (maxInclusive + 1 < 0)
            return (int) nextLong(minInclusive, maxInclusive);

        return random.nextInt(minInclusive, maxInclusive + 1);
    }

    public int nextInt(Range<Integer> range) {
        return (int) nextLong(range.cast(Integer::longValue));
    }

    @SuppressWarnings("unused")
    public float nextFloat() {
        return random.nextFloat();
    }

    public long nextLong() {
        return random.nextLong();
    }

    public long nextLong(long maxExclusive) {
        if (maxExclusive <= 0) {
            throw new IllegalArgumentException("bound must be positive: " + maxExclusive);
        }
        return nextLong(0, maxExclusive);
    }

    public long nextLong(Range<Long> range) {
        return switch (range.from().end()) {
            case EXCLUSIVE -> switch (range.to().end()) {
                case EXCLUSIVE -> random.nextLong(plusOne(range.from().value()), range.to().value());
                case INCLUSIVE -> random.nextLong(plusOne(range.from().value()), plusOne(range.to().value()));
            };
            case INCLUSIVE -> switch (range.to().end()) {
                case EXCLUSIVE -> random.nextLong(range.from().value(), range.to().value());
                case INCLUSIVE -> random.nextLong(range.from().value(), plusOne(range.to().value()));
            };
        };
    }

    private static long plusOne(long value) {
        return value == Long.MAX_VALUE ? value : value + 1;
    }

    /**
     * A random long value within given range.
     * If {@code min == max} then method always returns {@code min}.
     * Otherwise, {@code max} is exclusive.
     *
     * @param min lower bound (inclusive)
     * @param max upper bound (exclusive in most cases)
     * @return a random long value between {@code min} and {@code max}
     */
    public long nextLong(long min, long max) {
        return min == max ?
            min :
            random.nextLong(min, max);
    }

    public double nextDouble() {
        return random.nextDouble();
    }

    /**
     * @param min (inclusive)
     * @param max (inclusive)
     * @return a random double value between {@code min} and {@code max} (both inclusive)
     */
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
     * Returns a weighted random element from the given list, where each element is represented as a Map
     * containing a weight and the corresponding value.
     *
     * @param items A list of maps, where each map contains:
     *              - weight: A Double representing the weight of the element, influencing its selection probability.
     *              - value: The actual element of type T to be randomly selected based on its weight.
     * @param <T> The type of the element to be selected from the list. The value associated with the weight can be of any type.
     * @return A randomly selected element based on its weight.
     * @throws IllegalArgumentException if the list is null, empty, if any item in the list is null or empty,
     *                                  if any weight is null, non-positive, NaN, negative zero, infinite,
     *                                  or if the item does not contain 'weight' or 'value' keys.
     */
    public <T> T weightedArrayElement(List<Map<String, Object>> items) {
        validateItemsList(items);

        double totalWeight = calculateTotalWeight(items);
        double randomValue = random.nextDouble() * totalWeight;

        return selectWeightedElement(items, randomValue);
    }

    private void validateItemsList(List<Map<String, Object>> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("weightedArrayElement expects a non-empty list");
        }

        Set<Object> values = new HashSet<>();

        for (var item : items) {
            validateItem(item);
            assertUniqueValues(item, values);
        }
    }

    private void assertUniqueValues(Map<String, Object> item, Set<Object> values) {
        Object value = item.get(VALUE_KEY);
        if (!values.add(value)) {
            throw new IllegalArgumentException("Duplicate value found: " + value + ". Values must be unique.");
        }
    }

    private void validateItem(Map<String, Object> item) {
        if (item == null || item.isEmpty()) {
            throw new IllegalArgumentException("Item cannot be null or empty");
        }
        if (!item.containsKey(WEIGHT_KEY) || !item.containsKey(VALUE_KEY)) {
            throw new IllegalArgumentException("Each item must contain 'weight' and 'value' keys");
        }
        validateValue(item.get(VALUE_KEY));
        validateWeight(item.get(WEIGHT_KEY));
    }

    private void validateValue(Object valueObj) {
        if (valueObj == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
    }

    private void validateWeight(Object weightObj) {
        if (!(weightObj instanceof Double weight)) {
            throw new IllegalArgumentException("Weight must be a non-null Double");
        }
        if (weight <= 0 || Double.isNaN(weight) || Double.isInfinite(weight)) {
            throw new IllegalArgumentException("Weight must be a positive number and cannot be NaN or infinite");
        }
    }

    private double calculateTotalWeight(List<Map<String, Object>> items) {
        double totalWeight = 0.0;

        for (Map<String, Object> item : items) {
            double weight = (Double) item.get(WEIGHT_KEY);

            if (Double.MAX_VALUE - totalWeight < weight) {
                throw new IllegalArgumentException("Sum of the weights exceeds Double.MAX_VALUE");
            }

            totalWeight += weight;
        }

        return totalWeight;
    }

    private <T> T selectWeightedElement(List<Map<String, Object>> items, double randomValue) {
        double currentWeight = 0.0;

        for (Map<String, Object> item : items) {
            currentWeight += (Double) item.get(WEIGHT_KEY);
            if (randomValue < currentWeight) {
                return (T) item.get(VALUE_KEY);
            }
        }

        // Return the last element in case of rounding errors
        return (T) items.get(items.size() - 1).get(VALUE_KEY);
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
