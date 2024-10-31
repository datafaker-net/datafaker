package net.datafaker.service;

import net.datafaker.AbstractFakerTest;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import net.datafaker.utils.TestConstants;
import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author pmiklos
 */
class RandomServiceTest extends AbstractFakerTest {

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withSingleElement(RandomService randomService) {
        List<Map<String, Object>> items = List.of(
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_1, TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_1_WEIGHT)
        );

        String result = randomService.weightedArrayElement(items);
        assertThat(result).isEqualTo(TestConstants.ELEMENT_1);
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withMultipleElements(RandomService randomService) {
        List<Map<String, Object>> items = List.of(
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_1, TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_1_WEIGHT),
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_2, TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_2_WEIGHT),
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_3, TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_3_WEIGHT)
        );

        Map<String, Integer> counts = countResults(randomService, items);

        assertThat(counts).containsKeys(TestConstants.ELEMENT_1, TestConstants.ELEMENT_2, TestConstants.ELEMENT_3);
        assertThat(counts.get(TestConstants.ELEMENT_1)).isLessThan(counts.get(TestConstants.ELEMENT_2));
        assertThat(counts.get(TestConstants.ELEMENT_2)).isLessThan(counts.get(TestConstants.ELEMENT_3));
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withNullItems(RandomService randomService) {
        assertThatThrownBy(() -> randomService.weightedArrayElement(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("weightedArrayElement expects a non-empty list");
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withEmptyItems(RandomService randomService) {
        List<Map<String, Object>> items = Collections.emptyList();
        assertThatThrownBy(() -> randomService.weightedArrayElement(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("weightedArrayElement expects a non-empty list");
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withMissingWeightKey(RandomService randomService) {
        List<Map<String, Object>> items = List.of(
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_1),
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_2, TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> randomService.weightedArrayElement(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Each item must contain 'weight' and 'value' keys");
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withMissingValueKey(RandomService randomService) {
        List<Map<String, Object>> items = List.of(
            Map.of(TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_1_WEIGHT),
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_2, TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> randomService.weightedArrayElement(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Each item must contain 'weight' and 'value' keys");
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withInvalidWeightType(RandomService randomService) {
        List<Map<String, Object>> items = List.of(
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_1, TestConstants.WEIGHT_KEY, TestConstants.STRING_WEIGHT),
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_2, TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> randomService.weightedArrayElement(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Weight must be a non-null Double");
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withNaNWeight(RandomService randomService) {
        List<Map<String, Object>> items = List.of(
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_1, TestConstants.WEIGHT_KEY, Double.NaN),
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_2, TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> randomService.weightedArrayElement(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Weight must be a positive number and cannot be NaN or infinite");
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withZeroWeight(RandomService randomService) {
        List<Map<String, Object>> items = List.of(
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_1, TestConstants.WEIGHT_KEY, TestConstants.ZERO_WEIGHT),
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_2, TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> randomService.weightedArrayElement(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Weight must be a positive number and cannot be NaN or infinite");
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withNegativeWeight(RandomService randomService) {
        List<Map<String, Object>> items = List.of(
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_1, TestConstants.WEIGHT_KEY, TestConstants.NEGATIVE_WEIGHT),
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_2, TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> randomService.weightedArrayElement(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Weight must be a positive number and cannot be NaN or infinite");
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withInfiniteWeight(RandomService randomService) {
        List<Map<String, Object>> items = List.of(
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_1, TestConstants.WEIGHT_KEY, Double.POSITIVE_INFINITY),
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_2, TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> randomService.weightedArrayElement(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Weight must be a positive number and cannot be NaN or infinite");
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withNullItem(RandomService randomService) {
        List<Map<String, Object>> items = new ArrayList<>();
        items.add(null);
        items.add(Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_1, TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_1_WEIGHT));

        assertThatThrownBy(() -> randomService.weightedArrayElement(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Item cannot be null or empty");
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withEmptyItem(RandomService randomService) {
        List<Map<String, Object>> items = List.of(
            Map.of(),
            Map.of(TestConstants.VALUE_KEY, TestConstants.ELEMENT_1, TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_1_WEIGHT)
        );

        assertThatThrownBy(() -> randomService.weightedArrayElement(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Item cannot be null or empty");
    }

    @ParameterizedTest
    @MethodSource("randomServiceProvider")
    void testWeightedArrayElement_withNullValue(RandomService randomService) {
        List<Map<String, Object>> items = new ArrayList<>();
        
        Map<String, Object> itemWithNullValue = new HashMap<>();
        itemWithNullValue.put(TestConstants.VALUE_KEY, null);
        itemWithNullValue.put(TestConstants.WEIGHT_KEY, TestConstants.ELEMENT_1_WEIGHT);

        items.add(itemWithNullValue);

        assertThatThrownBy(() -> randomService.weightedArrayElement(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }

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
    void nextInt_returnsValueWithinGivenRange() {
        RandomService randomService = new RandomService();
        for (int i = 0; i < 10_000; i++) {
            assertThat(randomService.nextInt(2, 6))
                .isGreaterThanOrEqualTo(2)
                .isLessThanOrEqualTo(6);
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

    private Map<String, Integer> countResults(RandomService randomService, List<Map<String, Object>> items) {
        Map<String, Integer> counts = new HashMap<>();
        for (int i = 0; i < TestConstants.ITERATIONS; i++) {
            String result = randomService.weightedArrayElement(items);
            counts.merge(result, 1, Integer::sum);
        }
        return counts;
    }

    private static Stream<Arguments> randomServiceProvider() {
        return Stream.of(
            Arguments.of(new RandomService(), new RandomService(new Random()))
        );
    }
}
