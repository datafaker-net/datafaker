package net.datafaker;

/**
 * @since 0.8.0
 */
public class Lebowski extends AbstractProvider {

    public Lebowski(final Faker faker) {
        super(faker);
    }

    public String actor() {
        return resolve("lebowski.actors");
    }

    public String character() {
        return resolve("lebowski.characters");
    }

    public String quote() {
        return resolve("lebowski.quotes");
    }
}
