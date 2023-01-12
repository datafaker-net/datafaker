package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * The Venture Bros. is an American adult animated action comedy TV series.
 *
 * @since 1.8.0
 */
public class TheVentureBros extends AbstractProvider<ShowProviders> {

    protected TheVentureBros(ShowProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("the_venture_bros.character");
    }

    public String organization() {
        return resolve("the_venture_bros.organization");
    }

    public String vehicle() {
        return resolve("the_venture_bros.vehicle");
    }

    public String quote() {
        return resolve("the_venture_bros.quote");
    }

}
