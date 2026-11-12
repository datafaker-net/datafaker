package net.datafaker.service;

import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

/** A custom YAML choice, parsed once but sampled using the current Faker's random service. */
final class WeightedYamlValues {
    private final double[] cumulativeWeights;
    private final Object[] choices;

    private WeightedYamlValues(double[] cumulativeWeights, Object[] choices) {
        this.cumulativeWeights = cumulativeWeights;
        this.choices = choices;
    }

    @Nullable
    static WeightedYamlValues from(List<?> values, UnaryOperator<String> qualify) {
        // Ordinary lists (including lists of maps) retain their existing meaning.
        boolean weighted = values.stream().anyMatch(value -> value instanceof Map<?, ?> map
            && map.keySet().stream().anyMatch(key -> key instanceof String s && s.endsWith("%")));
        if (!weighted) return null;

        double[] cumulativeWeights = new double[values.size()];
        Object[] choices = new Object[values.size()];
        double cumulative = 0;
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < values.size(); i++) {
            Object value = values.get(i);
            if (!(value instanceof Map<?, ?> map) || map.size() != 1) {
                throw invalid("each entry must be a single percentage-to-value mapping");
            }
            var entry = map.entrySet().iterator().next();
            if (!(entry.getKey() instanceof String key) || !key.matches("[0-9]+(?:\\.[0-9]+)?%")) {
                throw invalid("percentage keys must be decimal strings such as '60%'");
            }
            BigDecimal percentage = new BigDecimal(key.substring(0, key.length() - 1));
            double weight = percentage.doubleValue();
            // Decimal strings can still overflow or underflow when converted to double.
            if (percentage.signum() <= 0 || !Double.isFinite(weight) || weight <= 0) {
                throw invalid("percentages must be finite and positive");
            }
            total = total.add(percentage);
            Object branch = entry.getValue();
            Object qualified;
            if (branch instanceof String string) {
                qualified = qualify.apply(string);
            } else if (branch instanceof List<?> list && !list.isEmpty()) {
                List<String> strings = new ArrayList<>(list.size());
                for (Object item : list) {
                    if (!(item instanceof String string)) {
                        throw invalid("list branches must contain only string templates");
                    }
                    strings.add(qualify.apply(string));
                }
                qualified = List.copyOf(strings);
            } else {
                throw invalid("each branch must be a string template or a non-empty list of string templates");
            }
            cumulative += weight;
            cumulativeWeights[i] = cumulative;
            choices[i] = qualified;
        }
        if (total.compareTo(BigDecimal.valueOf(100)) != 0) {
            throw invalid("percentages must total 100");
        }
        return new WeightedYamlValues(cumulativeWeights, choices);
    }

    String select(RandomService random) {
        Object selected = WeightedRandomSelector.selectWeightedElement(
            random.nextDouble() * cumulativeWeights[cumulativeWeights.length - 1], cumulativeWeights, choices);
        if (selected instanceof List<?> list) {
            return (String) list.get(list.size() == 1 ? 0 : random.nextInt(list.size()));
        }
        return (String) selected;
    }

    private static IllegalArgumentException invalid(String reason) {
        return new IllegalArgumentException("Invalid weighted YAML choices: " + reason);
    }
}
