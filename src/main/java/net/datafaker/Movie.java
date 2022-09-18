package net.datafaker;

/**
 * @since 1.5.0
 */
public class Movie extends AbstractProvider<IProviders> {

    protected Movie(BaseFaker faker) {
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
