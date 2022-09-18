package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * Brooklyn Nine-Nine is an American police procedural comedy television series.
 *
 * @since 1.3.0
 */
public class BrooklynNineNine extends AbstractProvider<MovieProviders> {

    protected BrooklynNineNine(MovieProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("brooklyn_nine_nine.characters");
    }

    public String quotes() {
        return resolve("brooklyn_nine_nine.quotes");
    }

}
