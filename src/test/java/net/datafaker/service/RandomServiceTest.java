package net.datafaker.service;

import net.datafaker.AbstractFakerTest;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author pmiklos
 */
class RandomServiceTest extends AbstractFakerTest {

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testPositiveBoundariesOnly(RandomService randomService) {
        assertThatThrownBy( () -> randomService.nextLong(0L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testLongWithinBoundary(RandomService randomService) {
        assertThat(randomService.nextLong(1)).isEqualTo(0L);

        for (int i = 1; i < 10; i++) {
            assertThat(randomService.nextLong(2)).isLessThan(2L);
        }
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testLongMaxBoundary(RandomService randomService) {
        assertThat(randomService.nextLong(Long.MAX_VALUE)).isGreaterThan(0L);
        assertThat(randomService.nextLong(Long.MAX_VALUE)).isLessThan(Long.MAX_VALUE);
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
        assertThat(l2).isEqualTo(1L);
        assertThat(l3).isEqualTo(836L);

        assertThat(b).isFalse();
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
        final Condition<Long> lessThanOrEqual = new Condition<>(t -> t <= 5_000_000_000L, "should be less than or equal 5_000_000_000L");
        final Condition<Long> greaterThanOrEqual = new Condition<>(t -> t >= -5_000_000_000L, "should be greater than or equal -5_000_000_000L");
        for (int i = 1; i < 1_000; i++) {
            assertThat(randomService.nextLong(-5_000_000_000L, 5_000_000_000L)).is(allOf(lessThanOrEqual, greaterThanOrEqual));
        }
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

    private static Stream<Arguments> randomServiceProvider() {
        return Stream.of(
            Arguments.of(new RandomService(), new RandomService(new Random()))
        );
    }
}
