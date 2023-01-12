package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.5.0
 */
public class Movie extends AbstractProvider<ShowProviders> {

    protected Movie(ShowProviders faker) {
        super(faker);
    }

    /**
     * This method generates a random quote from a movie.
     *
     * @return a string of quote from a movie.
     */
    public String quote() {
        return resolve("movie.quote");
    }
}
