package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.3.0
 */
public class SuperMario extends AbstractProvider<VideoGameProviders> {

    protected SuperMario(VideoGameProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("games.super_mario.characters");
    }

    public String games() {
        return resolve("games.super_mario.games");
    }

    public String locations() {
        return resolve("games.super_mario.locations");
    }

}
