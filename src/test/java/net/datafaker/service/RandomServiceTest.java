package net.datafaker.service;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SplittableRandom;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static net.datafaker.service.RandomServiceTest.Ring.DWARVES;
import static net.datafaker.service.RandomServiceTest.Ring.ELVES;
import static net.datafaker.service.RandomServiceTest.Ring.LORD;
import static net.datafaker.service.RandomServiceTest.Ring.MEN;
import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author pmiklos
 */
class RandomServiceTest {

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testPositiveBoundariesOnly(RandomService randomService) {
        assertThatThrownBy(() -> randomService.nextLong(0L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testLongWithinBoundary(RandomService randomService) {
        assertThat(randomService.nextLong(1)).isZero();

        for (int i = 1; i < 10; i++) {
            assertThat(randomService.nextLong(2)).isGreaterThanOrEqualTo(0).isLessThan(2L);
        }
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testLongMaxBoundary(RandomService randomService) {
        assertThat(randomService.nextLong(Long.MAX_VALUE)).isStrictlyBetween(0L, Long.MAX_VALUE);
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testIntInRange(RandomService randomService) {
        final Condition<Integer> lessThanOrEqual = new Condition<>(t -> t <= 5, "should be less than or equal 5");
        final Condition<Integer> greaterThanOrEqual = new Condition<>(t -> t >= -5, "should be greater than or equal -5");
        for (int i = 1; i < 100; i++) {
            assertThat(randomService.nextInt(-5, 5))
                .is(allOf(lessThanOrEqual, greaterThanOrEqual));
        }
    }

    @Test
    void nextInt_withoutBounds() {
        RandomService randomService = new RandomService();
        for (int i = 0; i < 100; i++) {
            assertThat(randomService.nextInt())
                .isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
    }

    @Test
    void nextLong_withoutBounds() {
        RandomService randomService = new RandomService();
        for (int i = 0; i < 100; i++) {
            assertThat(randomService.nextLong())
                .isBetween(Long.MIN_VALUE, Long.MAX_VALUE);
        }
    }

    @Test
    void nextInt_withUpperBound_exclusive() {
        RandomService randomService = new RandomService();
        assertThat(randomService.nextInt(1)).isEqualTo(0);

        for (int i = 0; i < 100; i++) {
            assertThat(randomService.nextInt(5)).isBetween(0, 4);
        }
    }

    @Test
    void nextLong_withUpperBound_exclusive() {
        RandomService randomService = new RandomService();
        assertThat(randomService.nextLong(1L)).isEqualTo(0);

        for (int i = 0; i < 100; i++) {
            assertThat(randomService.nextLong(5)).isBetween(0L, 4L);
        }
    }

    @Test
    void nextInt_withLowerAndUpperBounds() {
        RandomService randomService = new RandomService();
        assertThatThrownBy(() -> randomService.nextInt(20, 19))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Min (20) > Max (19)");

        assertThat(randomService.nextInt(42, 42)).isEqualTo(42);
        assertThat(randomService.nextInt(6, 7)).isBetween(6, 7);
        assertThat(randomService.nextInt(-3, 3)).isBetween(-3, 3);
    }

    @Test
    void nextInt_returnsValueWithinGivenRange() {
        RandomService randomService = new RandomService();
        Set<Integer> results = IntStream.range(0, 100)
            .mapToObj(__ -> randomService.nextInt(2, 6))
            .collect(toSet());

        assertThat(results).containsExactlyInAnyOrder(2, 3, 4, 5, 6);
    }

    @Test
    void nextInt_includesLowerBound() {
        RandomService randomService = new RandomService();
        assertThat(randomService.nextInt(Integer.MIN_VALUE, Integer.MIN_VALUE)).isEqualTo(Integer.MIN_VALUE);

        Set<Integer> results = IntStream.range(0, 100)
            .mapToObj(__ -> randomService.nextInt(Integer.MIN_VALUE, Integer.MIN_VALUE + 1))
            .collect(toSet());
        assertThat(results).containsExactlyInAnyOrder(Integer.MIN_VALUE, Integer.MIN_VALUE + 1);
    }

    @Test
    void nextInt_includesUpperBound() {
        RandomService randomService = new RandomService();
        assertThat(randomService.nextInt(Integer.MAX_VALUE, Integer.MAX_VALUE)).isEqualTo(Integer.MAX_VALUE);

        Set<Integer> all = IntStream.range(0, 100)
            .mapToObj(__ -> randomService.nextInt(Integer.MAX_VALUE - 1, Integer.MAX_VALUE))
            .collect(toSet());
        assertThat(all).containsExactlyInAnyOrder(Integer.MAX_VALUE - 1, Integer.MAX_VALUE);
    }

    @ParameterizedTest
    @CsvSource({
        "-3, -2",
        "-2147483648, -2147483647",
        "2147483646, 2147483647",
        "-2147483648, 2147483647",
        "-3, -1",
        "0, 2147483646",
        "-2, -2",
        "2147483647, 2147483647"
    })
    void nextInt_includesUpperBound(int min, int max) {
        RandomGenerator upperBoundGenerator = new RandomGenerator() {
            @Override
            public long nextLong() {
                return 0;
            }

            @Override
            public int nextInt(int origin, int bound) {
                return bound - 1;
            }

            @Override
            public long nextLong(long origin, long bound) {
                return bound - 1;
            }
        };
        assertThat(new RandomService(upperBoundGenerator).nextInt(min, max)).isEqualTo(max);
    }

    @Test
    void predictableRandomRange() {
        RandomService randomService = new RandomService(new Random(10));

        int i1 = randomService.nextInt();
        int i2 = randomService.nextInt(100);
        int i3 = randomService.nextInt(0, 100);

        float f1 = randomService.nextFloat();

        long l1 = randomService.nextLong();
        long l2 = randomService.nextLong(100);
        long l3 = randomService.nextLong(100, 1000);

        boolean b = randomService.nextBoolean();

        assertThat(i1).isEqualTo(-1157793070);
        assertThat(i2).isEqualTo(80);
        assertThat(i3).isEqualTo(35);

        assertThat(f1).isEqualTo(0.41291267F);

        assertThat(l1).isEqualTo(1092083446069765248L);
        assertThat(l2).isEqualTo(0L);
        assertThat(l3).isEqualTo(538L);

        assertThat(b).isFalse();
    }

    @Test
    void equalMinAndMax() {
        RandomService randomService = new RandomService();
        assertThat(randomService.nextInt(42, 42)).isEqualTo(42);
    }

    @RepeatedTest(10)
    void extremeIntegerValues() {
        RandomService randomService = new RandomService();
        assertThat(randomService.nextInt(1, Integer.MAX_VALUE)).isBetween(1, Integer.MAX_VALUE);
        assertThat(randomService.nextInt(Integer.MIN_VALUE, 0)).isBetween(Integer.MIN_VALUE, 0);
        assertThat(randomService.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE)).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testDoubleInRange(RandomService randomService) {
        final Condition<Double> lessThanOrEqual = new Condition<>(t -> t <= 5d, "should be less than or equal 5");
        final Condition<Double> greaterThanOrEqual = new Condition<>(t -> t >= -5d, "should be greater than or equal -5");
        for (int i = 1; i < 100; i++) {
            assertThat(randomService.nextDouble(-5, 5)).is(allOf(lessThanOrEqual, greaterThanOrEqual));
        }
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testLongInRange(RandomService randomService) {
        for (int i = 1; i < 1_000; i++) {
            assertThat(randomService.nextLong(-5_000_000_000L, 5_000_000_000L)).isBetween(-5_000_000_000L, 5_000_000_000L);
        }
    }

    @RepeatedTest(10)
    void extremeLongValues() {
        RandomService randomService = new RandomService();
        assertThat(randomService.nextLong(0, Long.MAX_VALUE - 1)).isBetween(0L, Long.MAX_VALUE - 1);
        assertThat(randomService.nextLong(Long.MIN_VALUE, 0)).isBetween(Long.MIN_VALUE, 0L);
        assertThat(randomService.nextLong(Long.MIN_VALUE, Long.MAX_VALUE)).isBetween(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    @Test
    void nextLong() {
        RandomService randomService = new RandomService();
        assertThat(randomService.nextLong(0L, 0L)).isEqualTo(0L);
        assertThat(randomService.nextLong(1L, 1L)).isEqualTo(1L);
        assertThat(randomService.nextLong(-42L, -42L)).isEqualTo(-42L);
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testHex(RandomService randomService) {
        assertThat(randomService.hex(8)).matches("^[0-9A-F]{8}$");
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testDefaultHex(RandomService randomService) {
        assertThat(randomService.hex()).matches("^[0-9A-F]{8}$");
    }

    @SuppressWarnings("removal")
    @Deprecated(since = "3.0.0", forRemoval = true)
    @Test
    void nextEnum() {
        RandomService randomService = new RandomService();
        Set<Ring> all = EnumSet.noneOf(Ring.class);
        for (int i = 1; i < 200; i++) {
            Ring actual = randomService.nextEnum(Ring.class);
            all.add(actual);
            assertThat(actual).isIn(ELVES, DWARVES, MEN, LORD);
        }
        assertThat(all).containsExactly(Ring.values());
    }

    @SuppressWarnings("removal")
    @Deprecated(since = "3.0.0", forRemoval = true)
    @ParameterizedTest
    @CsvSource({
        "12345, DWARVES, MEN, LORD, LORD, LORD",
        "42, MEN, ELVES, MEN, ELVES, DWARVES",
    })
    void predictableEnum(int seed, Ring expected1, Ring expected2, Ring expected3, Ring expected4, Ring expected5) {
        RandomService randomService = new RandomService(new Random(seed));
        assertThat(List.of(
            randomService.nextEnum(Ring.class),
            randomService.nextEnum(Ring.class),
            randomService.nextEnum(Ring.class),
            randomService.nextEnum(Ring.class),
            randomService.nextEnum(Ring.class)
        )).containsExactly(expected1, expected2, expected3, expected4, expected5);
    }

    @RepeatedTest(10)
    void customRandomGenerator() {
        RandomService randomService = new RandomService(new SplittableRandom());
        assertThat(randomService.nextInt()).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertThat(randomService.nextLong()).isBetween(Long.MIN_VALUE, Long.MAX_VALUE);
        assertThat(randomService.nextFloat()).isBetween(Float.MIN_VALUE, Float.MAX_VALUE);
        assertThat(randomService.nextDouble()).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);
        assertThat(randomService.nextBoolean()).isIn(List.of(true, false));
        assertThat(randomService.nextRandomBytes(8)).hasSize(8);
    }

    enum Ring {ELVES, DWARVES, MEN, LORD}

    private static Stream<Arguments> randomServiceProvider() {
        return Stream.of(
            Arguments.of(new RandomService(), new RandomService(new Random()))
        );
    }
}
