package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class TheItCrowd extends AbstractProvider<MovieProviders> {

    protected TheItCrowd(MovieProviders faker) {
        super(faker);
    }

    public String actors() {
        return resolve("the_it_crowd.actors");
    }

    public String characters() {
        return resolve("the_it_crowd.characters");
    }

    public String emails() {
        return resolve("the_it_crowd.emails");
    }

    public String quotes() {
        return resolve("the_it_crowd.quotes");
    }

}
