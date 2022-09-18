package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class ChuckNorris extends AbstractProvider<MovieProviders> {

    protected ChuckNorris(MovieProviders faker) {
        super(faker);
    }

    public String fact() {
        return resolve("chuck_norris.fact");
    }
}
