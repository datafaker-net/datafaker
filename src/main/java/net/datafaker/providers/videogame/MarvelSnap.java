package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

public class MarvelSnap extends AbstractProvider<VideoGameProviders> {

    protected MarvelSnap(final VideoGameProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("games.marvel_snap.characters");
    }

    public String zones() {
        return resolve("games.marvel_snap.zones");
    }

    public String events() {
        return resolve("games.marvel_snap.events");
    }

    public String rank() {
        return resolve("games.marvel_snap.rank");
    }
}
