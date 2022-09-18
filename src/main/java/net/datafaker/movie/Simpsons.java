package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * @since 1.5.0
 */
public class Simpsons extends AbstractProvider<MovieProviders> {

    public Simpsons(MovieProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("simpsons.characters");
    }

    public String location() {
        return resolve("simpsons.locations");
    }

    public String quote() {
        return resolve("simpsons.quotes");
    }
}
