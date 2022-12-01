package net.datafaker.configuration;

import java.util.*;

public class ProbabilityConfig {
    private final Map<Object, Double> probabilities = new HashMap<>();

    public ProbabilityConfig() {
    }

    public ProbabilityConfig with(boolean value, double probability) {
        probabilities.put(value, probability);
        return this;
    }

    public ProbabilityConfig with(int value, double probability) {
        probabilities.put(value, probability);
        return this;
    }

    public ProbabilityConfig with(Object value, double probability) {
        probabilities.put(value, probability);
        return this;
    }

    public Map<Object, Double> getProbabilities() {
        return probabilities;
    }
}
