package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NumberTest {

    private static final int RANDOMIZATION_QUALITY_RANGE_END = 1000;
    private static final int RANDOMIZATION_QUALITY_RANGE_STEP = 25;
    private static final int RANDOMIZATION_QUALITY_RANGE_START = RANDOMIZATION_QUALITY_RANGE_STEP;
    private static final int RANDOMIZATION_TESTS_MAX_NUMBERS_TO_GET = 1000;

    private static final double INDIVIDUAL_RUN_GT_PERCENT_UNIQUE = 0.8;
    private final double percentRunsGtUniquePercentage = 0.90;

    private final Faker faker = new Faker();

    @Test
    void testRandomDigit() {
        Set<Integer> nums = new HashSet<>(10);
        final Number number = faker.number();
        for (int i = 0; i < 1000; ++i) {
            int value = number.randomDigit();
            assertThat(value).isLessThanOrEqualTo(9)
                .isGreaterThanOrEqualTo(0);
            nums.add(value);
        }
        assertThat(nums).contains(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 8, 16, 25, 36})
    void randomDigits(int length) {
        final Number number = faker.number();
        int[] value = number.randomDigits(length);
        assertThat(value).hasSize(length);
        for (int i = 0; i < length; i++) {
            assertThat(value[i]).isLessThanOrEqualTo(9).isGreaterThanOrEqualTo(0);
        }
    }

    @Test
    void testRandomDigitNotZero() {
        Set<Integer> nums = new HashSet<>(10);
        final Number number = faker.number();
        for (int i = 0; i < 1000; ++i) {
            int value = number.randomDigitNotZero();
            assertThat(value).isLessThanOrEqualTo(9)
                .isGreaterThan(0);
            nums.add(value);
        }
        assertThat(nums).contains(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    void testRandomNumber() {
        long value = faker.number().randomNumber();
        assertThat(value).isLessThan(Long.MAX_VALUE);
    }

    @Test
    void testRandomNumberWithSingleDigitStrict() {
        final Number number = faker.number();
        for (int i = 0; i < 100; ++i) {
            long value = number.randomNumber(1);
            assertThat(value).isLessThan(10L)
                .isGreaterThanOrEqualTo(0L);
        }
    }

    @Test
    void randomNumberWithZeroDigits() {
        final Number number = faker.number();
        assertThatThrownBy(() -> number.randomNumber(0))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testRandomNumberWithGivenDigitsStrict() {
        final Number number = faker.number();
        for (int i = 1; i < 9; ++i) {
            for (int x = 0; x < 100; ++x) {
                long value = number.randomNumber(i);
                String stringValue = String.valueOf(value);
                assertThat(stringValue).hasSize(i);
            }
        }
    }

    @Test
    void testRandomDouble() {
        final Number number = faker.number();
        for (int i = 1; i < 5; ++i) {
            for (int x = 0; x < 100; ++x) {
                double value = number.randomDouble(i, 1, 1000);
                String strVal = BigDecimal.valueOf(value).stripTrailingZeros().toString();
                final int dotIndex = strVal.indexOf('.');
                if (dotIndex != -1 && strVal.indexOf('+') == -1) {
                    assertThat(strVal.length() - dotIndex - 1).isLessThanOrEqualTo(i);
                }
            }
        }
    }

    @Test
    void testNumberBetween() {
        Number number = faker.number();
        for (int i = 1; i < 100; ++i) {
            int v = number.numberBetween(0, i);
            assertThat(v).isLessThanOrEqualTo(i)
                .isGreaterThanOrEqualTo(0);
        }

        for (long i = 1L; i < 100L; ++i) {
            long v = number.numberBetween(0, i);
            assertThat(v).isLessThanOrEqualTo(i)
                .isGreaterThanOrEqualTo(0L);
        }

        int min1 = 1;
        long v1 = number.numberBetween(min1, 980000000L);
        assertThat(v1).isGreaterThan(min1)
            .isLessThan(980000000L);
    }

    @RepeatedTest(100)
    void testLongNumberBetweenRepeated() {
        long low = 1;
        long high = 10;
        long v = faker.number().numberBetween(low, high);
        assertThat(v).isLessThan(high)
            .isGreaterThanOrEqualTo(low);
    }

    @RepeatedTest(100)
    void testIntNumberBetweenRepeated() {
        int low = 1;
        int high = 10;
        int v = faker.number().numberBetween(low, high);
        assertThat(v).isLessThan(high)
            .isGreaterThanOrEqualTo(low);
    }

    @RepeatedTest(100)
    void testDoubleNumberBetweenRepeatedLowHigh() {
        double low = 1.0;
        double high = 10.0;
        double v = faker.number().numberBetween(low, high);
        assertThat(v).isLessThan(high)
            .isGreaterThanOrEqualTo(low);
    }

    @RepeatedTest(100)
    void testDoubleNumberBetweenRepeatedHighLow() {
        double low = 1.0;
        double high = 10.0;
        double v = faker.number().numberBetween(high, low);
        assertThat(v).isLessThan(high)
            .isGreaterThanOrEqualTo(low);
    }

    @Test
    void testNumberBetweenOneAndThree() {
        Set<Integer> nums = new HashSet<>(3);
        final int lowerLimit = 0;
        final int upperLimit = 3;
        final Number number = faker.number();
        for (int i = 0; i < 1000; ++i) {
            int value = number.numberBetween(lowerLimit, upperLimit);
            assertThat(value).isLessThan(upperLimit)
                .isGreaterThanOrEqualTo(lowerLimit);
            nums.add(value);
        }
        assertThat(nums).contains(0, 1, 2);
    }

    @Test
    void testLongBetweenOneAndThree() {
        Set<Long> nums = new HashSet<>(3);
        final long lowerLimit = 0;
        final long upperLimit = 3;
        final Number number = faker.number();
        for (int i = 0; i < 1000; ++i) {
            long value = number.numberBetween(lowerLimit, upperLimit);
            assertThat(value).isLessThan(upperLimit)
                .isGreaterThanOrEqualTo(lowerLimit);
            nums.add(value);
        }
        assertThat(nums).contains(0L, 1L, 2L);
    }

    @Test
    void numberBetweenIntIntZeroMinMax() {
        assertThat(faker.number().numberBetween(0, 0)).isZero();
        assertThat(faker.number().numberBetween(2, 2)).isEqualTo(2);
    }

    @Test
    void numberBetweenLongLongZeroMinMax() {
        assertThat(faker.number().numberBetween(0L, 0L)).isZero();
        assertThat(faker.number().numberBetween(2L, 2L)).isEqualTo(2L);
    }

    /**
     * Given a number of min/max ranges
     * for each min/max range, call {@link net.datafaker.providers.base.Number#randomDouble(int, int, int)} with min/max 'n' times
     * calculate the uniqueness for that given min/max range.
     * For all 'uniqueness' values
     * verify the percentage of 'uniqueness' ratios over 80% is 90%.
     * <p>
     * This isn't perfect, but it ensures a pretty good degree of uniqueness in the random number generation.
     */
    @Test
    void randomDoubleRandomizationQuality() {
        Function<Pair<Long, Long>, Double> minMaxRangeToUniquePercentageFunction = minMax -> {
            final int min = minMax.getLeft().intValue(), max = minMax.getRight().intValue();
            long numbersToGet = calculateNumbersToGet(min, max);

            return uniquePercentageOfResults(numbersToGet, () -> faker.number().randomDouble(0, min, max));
        };

        final double percentGreaterThan80Percent = randomizationQualityTest(minMaxRangeToUniquePercentageFunction);
        assertThat(percentGreaterThan80Percent).isGreaterThanOrEqualTo(percentRunsGtUniquePercentage);

        // this covers Issue # 121, the number of times the function is called with the MIN/MAX values here
        // is RANDOMIZATION_TESTS_MAX_NUMBERS_TO_GET
        final double extremeRunUniquePercent = minMaxRangeToUniquePercentageFunction.apply(Pair.of((long) Integer.MIN_VALUE, (long) Integer.MAX_VALUE));
        assertThat(extremeRunUniquePercent).isGreaterThanOrEqualTo(INDIVIDUAL_RUN_GT_PERCENT_UNIQUE);
    }

    /**
     * Given a number of min/max ranges
     * for each min/max range, call numberBetween with min/max 'n' times
     * calculate the uniqueness for that given min/max range.
     * For all 'uniqueness' values
     * verify the percentage of 'uniqueness' ratios over 80% is 90%.
     * <p>
     * This isn't perfect, but it ensures a pretty good degree of uniqueness in the random number generation.
     */
    @Test
    void numberBetweenIntIntRandomizationQuality() {
        Function<Pair<Long, Long>, Double> minMaxRangeToUniquePercentageFunction = minMax -> {
            final int min = minMax.getLeft().intValue();
            final int max = minMax.getRight().intValue();
            long numbersToGet = calculateNumbersToGet(min, max);

            return uniquePercentageOfResults(numbersToGet, () -> faker.number().numberBetween(min, max));
        };

        final double percentGreaterThan80Percent = randomizationQualityTest(minMaxRangeToUniquePercentageFunction);
        assertThat(percentGreaterThan80Percent).isGreaterThanOrEqualTo(percentRunsGtUniquePercentage);

        // this covers Issue # 121, the number of times the function is called with the MIN/MAX values here
        // is RANDOMIZATION_TESTS_MAX_NUMBERS_TO_GET
        final double extremeRunUniquePercent = minMaxRangeToUniquePercentageFunction.apply(Pair.of((long) Integer.MIN_VALUE, (long) Integer.MAX_VALUE));
        assertThat(extremeRunUniquePercent).isGreaterThanOrEqualTo(INDIVIDUAL_RUN_GT_PERCENT_UNIQUE);
    }

    /**
     * Given a number of min/max ranges
     * for each min/max range, call {@link net.datafaker.providers.base.Number#numberBetween(long, long)}  with min/max 'n' times
     * calculate the uniqueness for that given min/max range.
     * For all 'uniqueness' values
     * verify the percentage of 'uniqueness' ratios over 80% is 90%.
     * <p>
     * This isn't perfect, but it ensures a pretty good degree of uniqueness in the random number generation.
     */
    @Test
    void numberBetweenLongLongRandomizationQuality() {
        Function<Pair<Long, Long>, Double> minMaxRangeToUniquePercentageFunction = minMax -> {
            final long min = minMax.getLeft(), max = minMax.getRight();
            long numbersToGet = calculateNumbersToGet(min, max);

            return uniquePercentageOfResults(numbersToGet, () -> faker.number().numberBetween(min, max));
        };

        final double percentGreaterThan80Percent = randomizationQualityTest(minMaxRangeToUniquePercentageFunction);
        assertThat(percentGreaterThan80Percent).isGreaterThanOrEqualTo(percentRunsGtUniquePercentage);

        // this covers Issue # 121, the number of times the function is called with the MIN/MAX values here
        // is RANDOMIZATION_TESTS_MAX_NUMBERS_TO_GET.
        final double extremeRunUniquePercent = minMaxRangeToUniquePercentageFunction.apply(Pair.of(Long.MIN_VALUE, Long.MAX_VALUE));
        assertThat(extremeRunUniquePercent).isGreaterThanOrEqualTo(INDIVIDUAL_RUN_GT_PERCENT_UNIQUE);
    }

    @Test
    void testRandomDoubleMaxEqualsMin() {
        double actual = faker.number().randomDouble(1, 42, 42);

        double expected = BigDecimal.valueOf(42).doubleValue();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testDigit() {
        String digit = faker.number().digit();

        assertThat(digit).matches("[0-9]");
    }

    @Test
    void testDigits() {
        String digits = faker.number().digits(5);

        assertThat(digits).matches("[0-9]{5}");
    }

    /**
     * Over the series of numbers identified from RANDOMIZATION_QUALITY_RANGE_START to
     * RANDOMIZATION_QUALITY_RANGE_END, create a min/max range of -value/value and
     * with of those min/max values, call <em>percentUniqueRunner</em>.
     * <p>
     * Collect the number of calls to <em>percentUniqueRunner</em> that were
     * above the threshold and finally return that number divided by the total number of calls to
     * <em>percentUniqueRunner</em>.
     *
     * @return percent of percentUniqueRunner's results greater than the threshold
     */
    private double randomizationQualityTest(final Function<Pair<Long, Long>, Double> percentUniqueRunner) {

        final AtomicLong greaterThanThreshold = new AtomicLong();
        final AtomicLong total = new AtomicLong();

        for (long l = RANDOMIZATION_QUALITY_RANGE_START; l < RANDOMIZATION_QUALITY_RANGE_END; l += RANDOMIZATION_QUALITY_RANGE_STEP) {
            final double percentUnique = percentUniqueRunner.apply(Pair.of(-l, l));
            if (percentUnique > INDIVIDUAL_RUN_GT_PERCENT_UNIQUE) {
                greaterThanThreshold.incrementAndGet();
            }
            total.incrementAndGet();
        }

        return (double) greaterThanThreshold.get() / (double) total.get();
    }


    /**
     * Given a number of iterations, calls <em>callable</em> 'iterations' times and collects the results,
     * then calculates the number of results that were unique and returns the percentage that where unique.
     */
    private <T> double uniquePercentageOfResults(long iterations, Supplier<T> callable) {
            Set<T> values = new HashSet<>();
            for (long i = 0; i < iterations; i++) {
                values.add(callable.get());
            }
            return (double) values.size() / (double) iterations;
    }

    /**
     * given a range, what is the number of values to get within that range for the randomization quality tests.
     */
    private long calculateNumbersToGet(long min, long max) {
        long numbersToGet = Math.min(max / 4 - min / 4, RANDOMIZATION_TESTS_MAX_NUMBERS_TO_GET);
        if (numbersToGet == 0) numbersToGet = RANDOMIZATION_TESTS_MAX_NUMBERS_TO_GET;
        return numbersToGet;
    }

    @Test
    void testIntNumberBetweenQuality() {
        //test whether the fake number made by numberBetween(int min, int max)
        // is not randomly and evenly distributed
        // (The difference between the average is less than 10%)
        Map<Integer, Integer> map = new HashMap<>();
        Random random = new Random();
        int testCase = 100000;

        int min = Math.abs(random.nextInt());
        int max = min + Math.max(1, Math.abs(random.nextInt(100)));
        double mean = testCase / (double) (max - min);
        final Number number = faker.number();
        for (int j = 0; j < testCase; j++) {
            int r = number.numberBetween(min, max);
            map.merge(r, 1, Integer::sum);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int count = entry.getValue();
            assertThat((mean - count) / mean).isLessThan(0.2);
        }
    }

    @Test
    void testLongNumberBetweenQuality() {
        //test whether the fake number made by numberBetween(long min, long max)
        // is not randomly and evenly distributed
        // (The difference between the average is less than 10%)
        Map<Long, Integer> map = new HashMap<>();
        Random random = new Random();
        int testCase = 100000;

        long min = Math.abs(random.nextLong());
        long max = min + Math.max(1, Math.abs(random.nextInt(200)));
        double mean = testCase / (double) (max - min);
        final Number number = faker.number();
        for (int j = 0; j < testCase; j++) {
            long r = number.numberBetween(min, max);
            map.merge(r, 1, Integer::sum);
        }

        for (Map.Entry<Long, Integer> entry : map.entrySet()) {
            int count = entry.getValue();
            assertThat((mean - count) / mean).isLessThan(0.2);
        }

    }

    @Test
    void testNumberBetweenContain() {

        Set<Integer> ints = new HashSet<>();
        Set<Long> longs = new HashSet<>();
        Random random = new Random();
        int size = Math.abs(random.nextInt(100));

        //test whether NumberBetween(int min, int max) can
        // create all number between min and max(not included)
        // and not use crossing the border
        int minInt = Math.abs(random.nextInt());
        int maxInt = minInt + size;
        final Number number = faker.number();
        for (int i = 0; i < 10000; ++i) {
            int value = number.numberBetween(minInt, maxInt);
            assertThat(value).isBetween(minInt, maxInt)
                .isGreaterThanOrEqualTo(minInt);
            ints.add(value);
        }
        assertThat(ints).hasSize(Math.max(1, size));

        //test whether NumberBetween(long, long) can
        // create all number between min and max(not included)
        // and not use crossing the border
        long minLong = Math.abs(random.nextLong());
        long maxLong = minLong + size;
        for (int i = 0; i < 10000; ++i) {
            long value = number.numberBetween(minLong, maxLong);
            assertThat(value).isBetween(minLong, maxLong)
                .isGreaterThanOrEqualTo(minLong);
            longs.add(value);
        }
        assertThat(longs).hasSize(Math.max(1, size));
    }

    @Test
    void testNumberBetweenBorder() {

        Random random = new Random();

        //test whether NumberBetween(long, long) not use crossing the border
        for (int i = 0; i <= 100; i++) {

            //create long integer max and min
            long size, min = 0, max = -1;
            while (max < min) {
                size = Math.abs(random.nextLong());
                min = Math.abs(random.nextLong());
                max = min + size;
            }

            final Number number = faker.number();
            for (int j = 0; j < 100; j++) {
                long value = number.numberBetween(min, max);
                assertThat(value).isLessThan(max)
                    .isGreaterThanOrEqualTo(min);
            }
        }
    }

    @RepeatedTest(10)
    void testPositive() {
        assertThat(faker.number().positive()).isGreaterThan(0);
    }

    @RepeatedTest(10)
    void testNegative() {
        assertThat(faker.number().negative()).isLessThan(0);

    }
}
