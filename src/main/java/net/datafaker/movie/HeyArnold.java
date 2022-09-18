package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * Hey Arnold! is an American animated comedy television series created by Craig Bartlett.
 *
 * @since 1.4.0
 */
public class HeyArnold extends AbstractProvider<MovieProviders> {

    protected HeyArnold(MovieProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("hey_arnold.characters");
    }

    public String locations() {
        return resolve("hey_arnold.locations");
    }

    public String quotes() {
        return resolve("hey_arnold.quotes");
    }

}
