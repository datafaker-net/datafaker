package net.datafaker.providers.base;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @since 0.8.0
 */
public class Number extends AbstractProvider<BaseProviders> {

    private static final char[] DIGITS = "0123456789".toCharArray();

    protected Number(BaseProviders faker) {
        super(faker);
    }

    /**
     * Returns a random number from 0-9 (both inclusive)
     */
    public int randomDigit() {
        return faker.random().nextInt(0, 9);
    }

    /**
     * Returns a random number from 1-9 (both inclusive)
     */
    public int randomDigitNotZero() {
        return faker.random().nextInt(1, 9);
    }

    /**
     * Returns a positive number
     */
    public int positive() {
        return numberBetween(1, Integer.MAX_VALUE);
    }

    /**
     * Returns a negative number
     */
    public int negative() {
        return numberBetween(0, Integer.MIN_VALUE);
    }

    /**
     * @param min the lower bound (include min)
     * @param max the upper bound (not include max)
     * @return a random number on faker.number() between min and max
     * if min = max, return min
     */
    public int numberBetween(final int min, final int max) {
        if (min == max) return min;
        final int realMin = Math.min(min, max);
        final int realMax = Math.max(min, max);
        final int amplitude = realMax - realMin;
        if (isValidRange(realMin, realMax, amplitude)) {
            return faker.random().nextInt(amplitude) + realMin;
        }
        return (int) numberBetween(realMin, (long) realMax);
    }

    private boolean isValidRange(final int realMin, final int realMax, final int amplitude) {
        return amplitude >= realMin && (realMin >= 0 || amplitude >= realMax);
    }

    /**
     * @param min the lower bound (include min)
     * @param max the upper bound (not include max)
     * @return a random number on faker.number() between min and max
     * if min = max, return min
     */
    public long numberBetween(long min, long max) {
        if (min == max) return min;
        final long realMin = Math.min(min, max);
        final long realMax = Math.max(min, max);
        final long amplitude = realMax - realMin;
        if (isValidRange(realMin, realMax, amplitude)) {
            return faker.random().nextLong(realMax - realMin) + realMin;
        }
        return decimalBetween(realMin, realMax).longValue();
    }

    private boolean isValidRange(long realMin, long realMax, final long amplitude) {
        return amplitude >= realMin && (realMin >= 0 || amplitude >= realMax);
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
        int numberOfDigits = faker.random().nextInt(1, 10);
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
     * @param max the upper bound (not include max)
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
        final char[] tmp = new char[count];
        byte[] input = faker.random().nextRandomBytes(count);
        for (int i = 0; i < input.length; i++) {
            tmp[i] = DIGITS[Math.abs(input[i]) % 10];
        }
        return new String(tmp);
    }

    public String digit() {
        return digits(1);
    }
}
