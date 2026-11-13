package net.datafaker.providers.base;

/**
 * A generator for adjectives.
 *
 * @since 2.6.0
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
        return faker.options().option("adjective.positive", "adjective.negative");
    }
}