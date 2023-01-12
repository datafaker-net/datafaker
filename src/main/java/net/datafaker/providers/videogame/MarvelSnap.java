package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.8.0
 */
public class MarvelSnap extends AbstractProvider<VideoGameProviders> {

    protected MarvelSnap(final VideoGameProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("games.marvel_snap.characters");
    }

    public String zone() {
        return resolve("games.marvel_snap.zones");
    }

    public String event() {
        return resolve("games.marvel_snap.events");
    }

    public String rank() {
        return resolve("games.marvel_snap.rank");
    }
}
