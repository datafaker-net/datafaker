package net.datafaker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.withinPercentage;

class WeightedRandomSelectorTest {

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
    public static final double NEGATIVE_WEIGHT = -1.0;

    /**
     * Tests the constructor with a non-null Random instance.
     */
    @Test
    void testConstructorWithNonNullRandom() {
        Random providedRandom = new Random();
        WeightedRandomSelector selector = new WeightedRandomSelector(providedRandom);

        assertThat(selector.random()).isSameAs(providedRandom);
    }

    /**
     * Tests the constructor with a null Random instance.
     */
    @Test
    void testConstructorWithNullRandom() {
        WeightedRandomSelector selector = new WeightedRandomSelector(null);

        assertThat(selector.random()).isNotNull();
    }

    /**
     * Tests for exception scenarios using parameterized tests.
     */
    @ParameterizedTest
    @MethodSource("exceptionCasesProvider")
    void testWeightedArrayElementFailureCase(List<Map<String, Object>> items, String expectedMessage) {
        WeightedRandomSelector selector = new WeightedRandomSelector(new Random());
        assertThatThrownBy(() -> selector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(expectedMessage);
    }

    /**
     * Tests for invalid weight values using parameterized tests.
     */
    @ParameterizedTest
    @MethodSource("invalidWeightsProvider")
    void testWeightedArrayElementWithInvalidWeights(List<Map<String, Object>> items, String expectedMessage) {
        WeightedRandomSelector selector = new WeightedRandomSelector(new Random());
        assertThatThrownBy(() -> selector.select(items))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(expectedMessage);
    }

    /**
     * Tests the selection logic with specific random values using parameterized tests.
     */
    @ParameterizedTest
    @MethodSource("selectWeightedElementProvider")
    void testSelectWeightedElement(double randomValue, String expectedElement) {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT),
            Map.of(VALUE_KEY, ELEMENT_3, WEIGHT_KEY, ELEMENT_3_WEIGHT)
        );

        Object[] values = new Object[items.size()];
        double[] cumulativeWeights = WeightedRandomSelector.preprocessItems(items, values);

        String result = WeightedRandomSelector.selectWeightedElement(randomValue, cumulativeWeights, values);
        assertThat(result).isEqualTo(expectedElement);
    }

    /**
     * Tests selection with a single element using parameterized tests.
     */
    @ParameterizedTest
    @MethodSource("weightedRandomSelectorProvider")
    void testWeightedArrayElementWithSingleElement(WeightedRandomSelector selector) {
        List<Map<String, Object>> items = List.of(
            Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT)
        );

        String result = selector.select(items);
        assertThat(result).isEqualTo(ELEMENT_1);
    }

    /**
     * Tests selection with multiple elements and verifies the distribution using parameterized tests.
     */
    @ParameterizedTest
    @MethodSource("multipleElementsProvider")
    void testWeightedArrayElementWithMultipleElements(List<Map<String, Object>> items, Map<String, Integer> expectedCounts) {
        WeightedRandomSelector selector = new WeightedRandomSelector(new Random());

        Map<String, Integer> counts = countResults(selector, items);

        assertThat(counts.keySet()).containsExactlyInAnyOrderElementsOf(expectedCounts.keySet());
        for (String element : expectedCounts.keySet()) {
            assertThat(counts.get(element)).isCloseTo(expectedCounts.get(element), withinPercentage(20));
        }
    }

    static Stream<Arguments> exceptionCasesProvider() {
        return Stream.of(
            Arguments.of(
                null,
                "Input list cannot be null"
            ),
            Arguments.of(
                Collections.emptyList(),
                "Input list cannot be empty"
            ),
            Arguments.of(
                List.of(
                    Map.of(VALUE_KEY, ELEMENT_1),
                    Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT)
                ),
                "Each item must contain 'weight' and 'value' keys"
            ),
            Arguments.of(
                List.of(
                    Map.of(WEIGHT_KEY, ELEMENT_1_WEIGHT),
                    Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT)
                ),
                "Each item must contain 'weight' and 'value' keys"
            ),
            Arguments.of(
                List.of(
                    Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, STRING_WEIGHT),
                    Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT)
                ),
                "Weight must be a non-null Double"
            ),
            Arguments.of(
                Arrays.asList(
                    null,
                    Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT)
                ),
                "Item cannot be null"
            ),
            Arguments.of(
                List.of(
                    Map.of(),
                    Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT)
                ),
                "Item cannot be empty"
            ),
            Arguments.of(
                List.of(
                    new HashMap<String, Object>() {{
                        put(VALUE_KEY, null);
                        put(WEIGHT_KEY, ELEMENT_1_WEIGHT);
                    }}
                ),
                "Value cannot be null"
            ),
            Arguments.of(
                List.of(
                    Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, Double.MAX_VALUE - 1),
                    Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, Double.MAX_VALUE - 1)
                ),
                "Sum of the weights exceeds Double.MAX_VALUE"
            ),
            Arguments.of(
                List.of(
                    Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT),
                    Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_1_WEIGHT),
                    Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_1_WEIGHT),
                    Map.of(VALUE_KEY, ELEMENT_3, WEIGHT_KEY, ELEMENT_1_WEIGHT)
                ),
                "Duplicate value found: Element2. Values must be unique."
            )
        );
    }

    static Stream<Arguments> invalidWeightsProvider() {
        return Stream.of(
            Arguments.of(
                List.of(
                    Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, Double.NaN),
                    Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT)
                ),
                "Weight must be a non-negative number and cannot be NaN or infinite"
            ),
            Arguments.of(
                List.of(
                    Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, NEGATIVE_WEIGHT),
                    Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT)
                ),
                "Weight must be a non-negative number and cannot be NaN or infinite"
            ),
            Arguments.of(
                List.of(
                    Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, Double.POSITIVE_INFINITY),
                    Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT)
                ),
                "Weight must be a non-negative number and cannot be NaN or infinite"
            ),
            Arguments.of(
                List.of(
                    Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, 0.0),
                    Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, 0.0),
                    Map.of(VALUE_KEY, ELEMENT_3, WEIGHT_KEY, 0.0)
                ),
                "The total weight must be greater than 0. At least one item must have a positive weight"
            )
        );
    }

    static Stream<Arguments> selectWeightedElementProvider() {
        return Stream.of(
            Arguments.of(0.5, ELEMENT_1),
            Arguments.of(2.0, ELEMENT_2),
            Arguments.of(5.5, ELEMENT_3),
            Arguments.of(5.9, ELEMENT_3),
            Arguments.of(6.0, ELEMENT_3),
            Arguments.of(6.1, ELEMENT_3)
        );
    }

    static Stream<Arguments> multipleElementsProvider() {
        return Stream.of(
            Arguments.of(
                List.of(
                    Map.of(VALUE_KEY, ELEMENT_1, WEIGHT_KEY, ELEMENT_1_WEIGHT),
                    Map.of(VALUE_KEY, ELEMENT_2, WEIGHT_KEY, ELEMENT_2_WEIGHT),
                    Map.of(VALUE_KEY, ELEMENT_3, WEIGHT_KEY, ELEMENT_3_WEIGHT)
                ),
                Map.of(
                    ELEMENT_1, (int) (ITERATIONS * ELEMENT_1_WEIGHT / (ELEMENT_1_WEIGHT + ELEMENT_2_WEIGHT + ELEMENT_3_WEIGHT)),
                    ELEMENT_2, (int) (ITERATIONS * ELEMENT_2_WEIGHT / (ELEMENT_1_WEIGHT + ELEMENT_2_WEIGHT + ELEMENT_3_WEIGHT)),
                    ELEMENT_3, (int) (ITERATIONS * ELEMENT_3_WEIGHT / (ELEMENT_1_WEIGHT + ELEMENT_2_WEIGHT + ELEMENT_3_WEIGHT))
                )
            )
        );
    }

    private Map<String, Integer> countResults(WeightedRandomSelector selector, List<Map<String, Object>> items) {
        Map<String, Integer> counts = new HashMap<>();
        for (int i = 0; i < ITERATIONS; i++) {
            String result = selector.select(items);
            counts.merge(result, 1, Integer::sum);
        }
        return counts;
    }

    private static Stream<WeightedRandomSelector> weightedRandomSelectorProvider() {
        return Stream.of(new WeightedRandomSelector(new Random()));
    }
}
