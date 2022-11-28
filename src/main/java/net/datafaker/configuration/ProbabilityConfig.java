package net.datafaker.configuration;

public class ProbabilityConfig {
    public final static double DEFAULT_BOOLEAN_PROBABILITY = 0.5;
    private final double boolProbability;

    public ProbabilityConfig() {
        this(DEFAULT_BOOLEAN_PROBABILITY);
    }

    private ProbabilityConfig(double boolProbability) {
        this.boolProbability = boolProbability;
    }

    public ProbabilityConfig withBool(double probability) {
        return new ProbabilityConfig(probability);
    }

    public double getBoolProbability() {
        return boolProbability;
    }
}
