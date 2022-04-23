package net.datafaker;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @since 0.8.0
 */
public class Number {
    private final Faker faker;

    protected Number(Faker faker) {
        this.faker = faker;
    }

    /**
     * Returns a random number from 0-9 (both inclusive)
     */
    public int randomDigit() {
        return decimalBetween(0, 10).intValue();
    }

    /**
     * Returns a random number from 1-9 (both inclusive)
     */
    public int randomDigitNotZero() {
        return decimalBetween(1, 10).intValue();
    }

    /**
     * Returns a positive number
     */
    public int positive() { return decimalBetween(1, Integer.MAX_VALUE).intValue(); }

    /**
     * Returns a negative number
     */
    public int negative() { return decimalBetween(0, Integer.MIN_VALUE).intValue(); }

    /**
     * @param min the lower bound (include min)
     * @param max the lower bound (not include max)
     * @return a random number on faker.number() between min and max
     * if min = max, return min
     */
    public int numberBetween(int min, int max) {
        if (min == max) return min;
        return decimalBetween(min, max).intValue();
    }

    /**
     * @param min the lower bound (include min)
     * @param max the lower bound (not include max)
     * @return a random number on faker.number() between min and max
     * if min = max, return min
     */
    public long numberBetween(long min, long max) {
        if (min == max) return min;
        return decimalBetween(min, max).longValue();
    }

    /**
     * @param numberOfDigits the number of digits the generated value should have
     * @param strict         whether or not the generated value should have exactly <code>numberOfDigits</code>
     */
    public long randomNumber(int numberOfDigits, boolean strict) {
        long max = (long) Math.pow(10, numberOfDigits);
        if (strict) {
            long min = (long) Math.pow(10, numberOfDigits - 1);
            return faker.random().nextLong(max - min) + min;
        }

        return faker.random().nextLong(max);
    }

    /**
     * Returns a random number
     */
    public long randomNumber() {
        int numberOfDigits = decimalBetween(1, 10).intValue();
        return randomNumber(numberOfDigits, false);
    }

    public double randomDouble(int maxNumberOfDecimals, int min, int max) {
        return randomDouble(maxNumberOfDecimals, min, (long) max);
    }

    /**
     * Returns a random double
     *
     * @param maxNumberOfDecimals maximum number of places
     * @param min                 minimum value
     * @param max                 maximum value
     */
    public double randomDouble(int maxNumberOfDecimals, long min, long max) {
        return decimalBetween(min, max)
            .setScale(maxNumberOfDecimals, RoundingMode.HALF_DOWN)
            .doubleValue();
    }

    /**
     * @param min the lower bound (include min)
     * @param max the lower bound (not include max)
     * @return decimalBetween on faker.number() between min and max
     * if min = max, return min
     */
    private BigDecimal decimalBetween(long min, long max) {
        if (min == max) {
            return BigDecimal.valueOf(min);
        }

        final BigDecimal trueMin = BigDecimal.valueOf(min);
        final BigDecimal trueMax = BigDecimal.valueOf(max);
        final BigDecimal random = BigDecimal.valueOf(faker.random().nextDouble());

        return trueMin.add(trueMax.subtract(trueMin).multiply(random));
    }

    public String digits(int count) {
        final StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < count; i++) {
            tmp.append(randomDigit());
        }
        return tmp.toString();
    }

    public String digit() {
        return digits(1);
    }
}
