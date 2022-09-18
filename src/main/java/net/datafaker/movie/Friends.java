package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Friends extends AbstractProvider<MovieProviders> {

    protected Friends(MovieProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("friends.characters");
    }

    public String location() {
        return resolve("friends.locations");
    }

    public String quote() {
        return resolve("friends.quotes");
    }
}
