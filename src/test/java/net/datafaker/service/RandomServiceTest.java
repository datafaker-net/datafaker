package net.datafaker.service;

import net.datafaker.AbstractFakerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.CombinableMatcher.both;

/**
 * @author pmiklos
 */
public class RandomServiceTest extends AbstractFakerTest {

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    public void testPositiveBoundariesOnly(RandomService randomService) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> randomService.nextLong(0L));
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    public void testLongWithinBoundary(RandomService randomService) {
        assertThat(randomService.nextLong(1), is(0L));

        for (int i = 1; i < 10; i++) {
            assertThat(randomService.nextLong(2), lessThan(2L));
        }
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    public void testLongMaxBoundary(RandomService randomService) {
        assertThat(randomService.nextLong(Long.MAX_VALUE), greaterThan(0L));
        assertThat(randomService.nextLong(Long.MAX_VALUE), lessThan(Long.MAX_VALUE));
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    public void testIntInRange(RandomService randomService) {
        for (int i = 1; i < 100; i++) {
            assertThat(randomService.nextInt(-5, 5), both(lessThanOrEqualTo(5)).and(greaterThanOrEqualTo(-5)));
        }
    }

    @Test
    public void predictableRandomRange() {
        RandomService randomService = new RandomService(new Random(10));

        int i1 = randomService.nextInt();
        int i2 = randomService.nextInt(100);
        int i3 = randomService.nextInt(0, 100);

        float f1 = randomService.nextFloat();

        long l1 = randomService.nextLong();
        long l2 = randomService.nextLong(100);
        long l3 = randomService.nextLong(100, 1000);

        boolean b = randomService.nextBoolean();

        assertThat(i1, is(equalTo(-1157793070)));
        assertThat(i2, is(equalTo(80)));
        assertThat(i3, is(equalTo(35)));

        assertThat(f1, is(equalTo(0.41291267F)));

        assertThat(l1, is(equalTo(1092083446069765248L)));
        assertThat(l2, is(equalTo(1L)));
        assertThat(l3, is(equalTo(836L)));

        assertThat(b, is(equalTo(false)));
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    public void testDoubleInRange(RandomService randomService) {
        for (int i = 1; i < 100; i++) {
            assertThat(randomService.nextDouble(-5, 5), both(lessThanOrEqualTo(5.0)).and(greaterThanOrEqualTo(-5.0)));
        }
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    public void testLongInRange(RandomService randomService) {
        for (int i = 1; i < 1_000; i++) {
            assertThat(randomService.nextLong(-5_000_000_000L, 5_000_000_000L), both(lessThanOrEqualTo(5_000_000_000L)).and(greaterThanOrEqualTo(-5_000_000_000L)));
        }
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    public void testHex(RandomService randomService) {
        assertThat(randomService.hex(8), matchesRegularExpression("^[0-9A-F]{8}$"));
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    public void testDefaultHex(RandomService randomService) {
        assertThat(randomService.hex(), matchesRegularExpression("^[0-9A-F]{8}$"));
    }

    private static Stream<Arguments> randomServiceProvider() {
        return Stream.of(
            Arguments.of(new RandomService(), new RandomService(new Random()))
        );
    }
}
