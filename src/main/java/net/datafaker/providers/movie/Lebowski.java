package net.datafaker.providers.movie;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Lebowski extends AbstractProvider<MovieProviders> {

    public Lebowski(final MovieProviders faker) {
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
