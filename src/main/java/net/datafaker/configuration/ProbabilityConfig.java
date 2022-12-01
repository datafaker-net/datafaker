package net.datafaker.configuration;

import java.util.*;

public class ProbabilityConfig {
    public final static double DEFAULT_BOOLEAN_PROBABILITY = 0.5;
    private final double boolProbability;
    private final Map<Integer, Double> intProbability = new HashMap<>();

    public ProbabilityConfig() {
        this(DEFAULT_BOOLEAN_PROBABILITY);
    }

    private ProbabilityConfig(double boolProbability) {
        this.boolProbability = boolProbability;
    }

    public ProbabilityConfig withBool(double probability) {
        return new ProbabilityConfig(probability);
    }

    public ProbabilityConfig withInt(int value, double probability) {
        intProbability.put(value, probability);
        return this;
    }

    public double getBoolProbability() {
        return boolProbability;
    }

    public Map<Integer, Double> getIntProbability() {
        return intProbability;
    }
}
