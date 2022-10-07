package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Fallout: A Post Nuclear Role Playing Game is a 1997 role-playing video game developed and published by Interplay Productions.
 *
 * @since 1.6.0
 */
public class Fallout extends AbstractProvider<VideoGameProviders> {

    protected Fallout(VideoGameProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("fallout.characters");
    }

    public String faction() {
        return resolve("fallout.factions");
    }

    public String location() {
        return resolve("fallout.locations");
    }

    public String quote() {
        return resolve("fallout.quotes");
    }
}
