package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Half-Life is a series of first-person shooter games developed and published by Valve.
 *
 * @since 1.8.0
 */
public class HalfLife extends AbstractProvider<VideoGameProviders> {

    protected HalfLife(VideoGameProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("games.half_life.character");
    }

    public String enemy() {
        return resolve("games.half_life.enemy");
    }

    public String location() {
        return resolve("games.half_life.location");
    }

}
