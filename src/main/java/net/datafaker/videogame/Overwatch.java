package net.datafaker.videogame;

import net.datafaker.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Overwatch extends AbstractProvider<VideoGameProviders> {

    protected Overwatch(final VideoGameProviders faker) {
        super(faker);
    }

    public String hero() {
        return resolve("games.overwatch.heroes");
    }

    public String location() {
        return resolve("games.overwatch.locations");
    }

    public String quote() {
        return resolve("games.overwatch.quotes");
    }
}
