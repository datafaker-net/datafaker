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
        if (amplitude >= 0) {
            return faker.random().nextInt(amplitude) + realMin;
        }
        return (int) numberBetween(realMin, (long) realMax);
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
        if (amplitude >= 0) {
            return faker.random().nextLong(amplitude) + realMin;
        }
        return decimalBetween(realMin, realMax).longValue();
    }

    /**
     * @param numberOfDigits the number of digits the generated value should have
     * @param strict         whether or not the generated value should have exactly <code>numberOfDigits</code>
     */
    public long randomNumber(int numberOfDigits, boolean strict) {
        long max = pow(10, numberOfDigits);
        if (strict) {
            long min = max / 10;
            return faker.random().nextLong(max - min) + min;
        }

        return faker.random().nextLong(max);
    }

    private long pow(long value, int d) {
        if (d == 0) return 1;
        if (d == 1) return value;
        if (d % 2 == 0) {
            long pow = pow(value, d / 2);
            return pow * pow;
        } else {
            return value * pow(value, d - 1);
        }
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
