package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Kaamelott extends AbstractProvider<MovieProviders> {

    protected Kaamelott(MovieProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("kaamelott.characters");
    }

    public String quote() {
        return resolve("kaamelott.quotes");
    }
}
