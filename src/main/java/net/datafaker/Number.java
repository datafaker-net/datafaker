package net.datafaker;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Number {
    private final Faker faker;

    protected Number(Faker faker) {
        this.faker = faker;
    }

    /**
     * Returns a random number from 0-9 (both inclusive)
     */
    public int randomDigit() {
        return numberBetween(0, 10);
    }

    /**
     * Returns a random number from 1-9 (both inclusive)
     */
    public int randomDigitNotZero() {
        return intBetween(1, 10);
    }

    /**
     * @param min the lower bound (include min)
     * @param max the lower bound (not include max)
     * @return a random number on faker.number() between min and max
     * if min = max, return min
     */
    public int numberBetween(int min, int max) {
        if (min == max) return min;
        return intBetween(min, max);
    }

    /**
     * @param min the lower bound (include min)
     * @param max the lower bound (not include max)
     * @return a random number on faker.number() between min and max
     * if min = max, return min
     */
    public long numberBetween(long min, long max) {
        if (min == max) return min;
        return longBetween(min, max);
    }

    /**
     * @param numberOfDigits the number of digits the generated value should have
     * @param strict         whether or not the generated value should have exactly <code>numberOfDigits</code>
     */
    public long randomNumber(int numberOfDigits, boolean strict) {
        long max = 1;
        for (int i = 0; i < numberOfDigits; i++) max *= 10;
        if (strict) {
            long min = numberOfDigits <= 1 ? 0 : 1;
            for (int i = 0; i < numberOfDigits - 1; i++) min *= 10;
            return faker.random().nextLong(min, max);
        }

        return faker.random().nextLong(max);
    }

    /**
     * Returns a random number
     */
    public long randomNumber() {
        int numberOfDigits = intBetween(1, 10);
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

    private int intBetween(int min, int max) {
        return faker.random().nextInt(min, max);
    }

    private long longBetween(long min, long max) {
        return faker.random().nextLong(min, max);
    }

    public String digits(int count) {
        final char[] result = new char[count];
        for (int i = 0; i < count; i++) {
            result[i] = (char) ('0' + randomDigit());
        }
        return String.valueOf(result);
    }

    public String digit() {
        return digits(1);
    }
}
