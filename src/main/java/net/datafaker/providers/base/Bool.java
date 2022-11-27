package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Bool extends AbstractProvider<BaseProviders> {

    protected Bool(BaseProviders faker) {
        super(faker);
    }

    public boolean bool() {
        return faker.random().nextBoolean();
    }

    /**
     * Returns {@code true} with probability - `probability`
     * and {@code false} with probability 1 - `probability` argument.
     *
     * @param probability the probability of returning {@code true}
     * @return {@code true} with probability {@code probability} and
     * {@code false} with probability {@code 1 - probability}
     * @throws IllegalArgumentException if {@code probability} &lt; {@code 0} or {@code probability} &gt; {@code 1.0}
     */
    public boolean bool(double probability) {
        if (probability < 0.0 || probability > 1.0) {
            throw new IllegalArgumentException("Probability parameter should be between [0.0, 1.0]: " + probability);
        }

        return faker.random().nextDouble() < probability;
    }
}
