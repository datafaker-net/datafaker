package net.datafaker.service;

import net.datafaker.annotations.InternalApi;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * A utility class for selecting a random element from a list based on assigned weights.
 **/
public record WeightedRandomSelector(Random random) {
    private static final String WEIGHT_KEY = "weight";
    private static final String VALUE_KEY = "value";

    public WeightedRandomSelector(Random random) {
        this.random = random != null ? random : new Random();
    }

    /**
     * Returns a weighted random element from the given list, where each element is represented as a Map
     * containing a weight and the corresponding value.
     * <p>
     *
     * @param items A list of maps, where each map contains:
     *              - weight: A Double representing the weight of the element, influencing its selection probability.
     *              - value: The actual element of type T to be randomly selected based on its weight.
     * @param <T>   The type of the element to be selected from the list. The value associated with the weight can be of any type.
     * @return A randomly selected element based on its weight.
     * @throws IllegalArgumentException if:
     *                                  - the list is null or empty,
     *                                  - any item in the list is null or empty,
     *                                  - the item does not contain 'weight' or 'value' keys,
     *                                  - any weight is null, non-positive, NaN or infinite,
     *                                  - any values in the list are not unique or null,
     *                                  - the sum of weights exceeds Double.MAX_VALUE.
     */
    public <T> T select(List<Map<String, Object>> items) {
        validateItemsList(items);

        Object[] values = new Object[items.size()];
        double[] cumulativeWeights = preprocessItems(items, values);

        double randomValue = random.nextDouble() * cumulativeWeights[cumulativeWeights.length - 1];
        return selectWeightedElement(randomValue, cumulativeWeights, values);
    }

    private static void validateItemsList(List<Map<String, Object>> items) {
        if (items == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be empty");
        }

        Set<Object> uniqueValues = new HashSet<>();

        for (var item : items) {
            validateItem(item);
            assertUniqueValues(item, uniqueValues);
        }
    }

    private static void assertUniqueValues(Map<String, Object> item, Set<Object> values) {
        Object value = item.get(VALUE_KEY);
        if (!values.add(value)) {
            throw new IllegalArgumentException("Duplicate value found: " + value + ". Values must be unique.");
        }
    }

    private static void validateItem(Map<String, Object> item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (item.isEmpty()) {
            throw new IllegalArgumentException("Item cannot be empty");
        }
        if (!item.containsKey(WEIGHT_KEY) || !item.containsKey(VALUE_KEY)) {
            throw new IllegalArgumentException("Each item must contain 'weight' and 'value' keys");
        }
        validateValue(item.get(VALUE_KEY));
        validateWeight(item.get(WEIGHT_KEY));
    }

    private static void validateValue(Object valueObj) {
        if (valueObj == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
    }

    private static void validateWeight(Object weightObj) {
        if (!(weightObj instanceof Double weight)) {
            throw new IllegalArgumentException("Weight must be a non-null Double");
        }
        if (weight < 0 || Double.isNaN(weight) || Double.isInfinite(weight)) {
            throw new IllegalArgumentException("Weight must be a non-negative number and cannot be NaN or infinite");
        }
    }

    private static void validateTotalWeight(double totalWeight) {
        if (totalWeight <= 0) {
            throw new IllegalArgumentException("The total weight must be greater than 0. At least one item must have a positive weight");
        }
    }

    @InternalApi
    static double[] preprocessItems(List<Map<String, Object>> items, Object[] values) {
        double[] cumulativeWeights = new double[items.size()];

        double totalWeight = 0.0;
        for (int i = 0; i < items.size(); i++) {
            double weight = (Double) items.get(i).get(WEIGHT_KEY);
            if (Double.MAX_VALUE - totalWeight < weight) {
                throw new IllegalArgumentException("Sum of the weights exceeds Double.MAX_VALUE");
            }
            totalWeight += weight;
            cumulativeWeights[i] = totalWeight;
            values[i] = items.get(i).get(VALUE_KEY);
        }

        validateTotalWeight(totalWeight);

        return cumulativeWeights;
    }

    @InternalApi
    static <T> T selectWeightedElement(double randomValue, double[] cumulativeWeights, Object[] values) {
        int index = Arrays.binarySearch(cumulativeWeights, randomValue);
        index = (index < 0) ? -index - 1 : index;

        if (index >= cumulativeWeights.length) {
            index = cumulativeWeights.length - 1;
        }

        return (T) values[index];
    }
}
