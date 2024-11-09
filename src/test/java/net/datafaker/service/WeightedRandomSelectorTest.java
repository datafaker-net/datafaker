package net.datafaker.service;

import net.datafaker.AbstractFakerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WeightedRandomSelectorTest extends AbstractFakerTest {

    public static final String WEIGHT_KEY = "weight";
    public static final String VALUE_KEY = "value";
    public static final String ELEMENT_1 = "Element1";
    public static final String ELEMENT_2 = "Element2";
    public static final String ELEMENT_3 = "Element3";
    public static final String STRING_WEIGHT = "1.0";
    public static final int ITERATIONS = 1000;
    public static final double ELEMENT_1_WEIGHT = 1.0;
    public static final double ELEMENT_2_WEIGHT = 2.0;
    public static final double ELEMENT_3_WEIGHT = 3.0;
    public static final double ZERO_WEIGHT = 0.0;
    public static final double NEGATIVE_WEIGHT = -1.0;

    private WeightedRandomSelector weightedRandomSelector;

    @BeforeEach
    void setUp() {
        weightedRandomSelector = new WeightedRandomSelector(new Random());
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withSingleElement(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT)
        );

        String result = weightedRandomSelector.select(items);
        assertThat(result).isEqualTo(ELEMENT_1);
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withMultipleElements(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_3, WEIGHT_KEY, ELEMENT_3_WEIGHT)
        );

        Map<String, Integer> counts = countResults(weightedRandomSelector, items);

        assertThat(counts).containsKeys(ELEMENT_1, ELEMENT_2, ELEMENT_3);
        assertThat(counts.get(ELEMENT_1)).isLessThan(counts.get(ELEMENT_2));
        assertThat(counts.get(ELEMENT_2)).isLessThan(counts.get(ELEMENT_3));
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withNullItems(WeightedRandomSelector weightedRandomSelector) {
        assertThatThrownBy(() -> weightedRandomSelector.select(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("weightedArrayElement expects a non-empty list");
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withEmptyItems(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = Collections.emptyList();
        assertThatThrownBy(() -> weightedRandomSelector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("weightedArrayElement expects a non-empty list");
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withMissingWeightKey(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> weightedRandomSelector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Each item must contain 'weight' and 'value' keys");
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withMissingValueKey(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = List.of(
            Map.of(WEIGHT_KEY, ELEMENT_1_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> weightedRandomSelector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Each item must contain 'weight' and 'value' keys");
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withInvalidWeightType(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, STRING_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> weightedRandomSelector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Weight must be a non-null Double");
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withNaNWeight(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, Double.NaN),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> weightedRandomSelector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Weight must be a positive number and cannot be NaN or infinite");
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withZeroWeight(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ZERO_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> weightedRandomSelector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Weight must be a positive number and cannot be NaN or infinite");
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withNegativeWeight(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, NEGATIVE_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> weightedRandomSelector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Weight must be a positive number and cannot be NaN or infinite");
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withInfiniteWeight(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, Double.POSITIVE_INFINITY),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT)
        );

        assertThatThrownBy(() -> weightedRandomSelector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Weight must be a positive number and cannot be NaN or infinite");
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withNullItem(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = new ArrayList<>();
        items.add(null);
        items.add(Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT));

        assertThatThrownBy(() -> weightedRandomSelector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Item cannot be null or empty");
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withEmptyItem(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = List.of(
            Map.of(),
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT)
        );

        assertThatThrownBy(() -> weightedRandomSelector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Item cannot be null or empty");
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElement_withNullValue(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = new ArrayList<>();

        Map<String, Object> itemWithNullValue = new HashMap<>();
        itemWithNullValue.put(VALUE_KEY, null);
        itemWithNullValue.put(WEIGHT_KEY, ELEMENT_1_WEIGHT);

        items.add(itemWithNullValue);

        assertThatThrownBy(() -> weightedRandomSelector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Value cannot be null");
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testCalculateTotalWeight_withOverflowingWeights(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, Double.MAX_VALUE - 1),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, Double.MAX_VALUE - 1)
        );

        assertThatThrownBy(() -> weightedRandomSelector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Sum of the weights exceeds Double.MAX_VALUE");
    }

    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testCalculateTotalWeight_withDuplicateKeys(WeightedRandomSelector weightedRandomSelector) {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_1_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_1_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_3, WEIGHT_KEY, ELEMENT_1_WEIGHT)
        );

        assertThatThrownBy(() -> weightedRandomSelector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Duplicate value found: Element2. Values must be unique.");
    }

    @Test
    void testSelectWeightedElement_randomValueInRange() {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_3, WEIGHT_KEY, ELEMENT_3_WEIGHT)
        );

        Object[] values = new Object[items.size()];
        double[] cumulativeWeights = weightedRandomSelector.preprocessItems(items, values);

        assertThat((String) weightedRandomSelector.selectWeightedElement(0.5, cumulativeWeights, values)).isEqualTo(ELEMENT_1);
        assertThat((String) weightedRandomSelector.selectWeightedElement(2.0, cumulativeWeights, values)).isEqualTo(ELEMENT_2);
        assertThat((String) weightedRandomSelector.selectWeightedElement(5.5, cumulativeWeights, values)).isEqualTo(ELEMENT_3);
    }

    @Test
    void testSelectWeightedElement_randomValueTriggersLastIndex() {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_3, WEIGHT_KEY, ELEMENT_3_WEIGHT)
        );

        Object[] values = new Object[items.size()];
        double[] cumulativeWeights = weightedRandomSelector.preprocessItems(items, values);

        assertThat((String) weightedRandomSelector.selectWeightedElement(5.9, cumulativeWeights, values)).isEqualTo(ELEMENT_3);
    }

    @Test
    void testSelectWeightedElement_randomValueAtLastBoundary() {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_3, WEIGHT_KEY, ELEMENT_3_WEIGHT)
        );

        Object[] values = new Object[items.size()];
        double[] cumulativeWeights = weightedRandomSelector.preprocessItems(items, values);

        assertThat((String) weightedRandomSelector.selectWeightedElement(6.0, cumulativeWeights, values)).isEqualTo(ELEMENT_3);
    }

    @Test
    void testSelectWeightedElement_randomValueExceedsCumulativeWeight() {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_3, WEIGHT_KEY, ELEMENT_3_WEIGHT)
        );

        Object[] values = new Object[items.size()];
        double[] cumulativeWeights = weightedRandomSelector.preprocessItems(items, values);

        double randomValue = 6.1;

        assertThat((String) weightedRandomSelector.selectWeightedElement(randomValue, cumulativeWeights, values)).isEqualTo(ELEMENT_3);
    }

    @Test
    void testConstructorWithNonNullRandom() {
        Random providedRandom = new Random();
        WeightedRandomSelector selector = new WeightedRandomSelector(providedRandom);

        assertThat(selector.getRandom()).isSameAs(providedRandom);
    }

    @Test
    void testConstructorWithNullRandom() {
        WeightedRandomSelector selector = new WeightedRandomSelector(null);

        assertThat(selector.getRandom()).isNotNull();
    }

    private Map<String, Integer> countResults(WeightedRandomSelector weightedRandomSelector, List<Map<String, Object>> items) {
        Map<String, Integer> counts = new HashMap<>();
        for (int i = 0; i < ITERATIONS; i++) {
            String result = weightedRandomSelector.select(items);
            counts.merge(result, 1, Integer::sum);
        }
        return counts;
    }

    private static Stream<WeightedRandomSelector> weightedRandomSelectorProvider() {
        return Stream.of(new WeightedRandomSelector(new Random()));
    }
}
