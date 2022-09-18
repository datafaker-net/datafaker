package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * @since 0.9.0
 */
public class Babylon5 extends AbstractProvider<MovieProviders> {

    protected Babylon5(MovieProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("babylon5.characters");
    }

    public String quote() {
        return resolve("babylon5.quotes");
    }
}
