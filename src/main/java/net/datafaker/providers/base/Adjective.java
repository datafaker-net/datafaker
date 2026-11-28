package net.datafaker.providers.base;

/**
 * Provides adjectives from locale-specific positive and negative lists.
 * Use {@link Word#adjective()} to sample the general adjective vocabulary.
 *
 * @since 3.0.0
 */
public class Adjective extends AbstractProvider<BaseProviders> {

    protected Adjective(BaseProviders faker) {
        super(faker);
    }

    /**
     * Generates a random positive adjective.
     */
    public String positive() {
        return resolve("adjective.positive");
    }

    /**
     * Generates a random negative adjective.
     */
    public String negative() {
        return resolve("adjective.negative");
    }

    /**
     * Generates a random adjective (positive or negative).
     */
    public String any() {
        return faker.random().nextBoolean() ? positive() : negative();
    }
}
