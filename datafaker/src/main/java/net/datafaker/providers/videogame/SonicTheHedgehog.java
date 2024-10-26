package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Sonic the Hedgehog is a Japanese video game series and media franchise created by Sega.
 *
 * @since 1.8.0
 */
public class SonicTheHedgehog extends AbstractProvider<VideoGameProviders> {

    protected SonicTheHedgehog(VideoGameProviders faker) {
        super(faker);
    }

    public String zone() {
        return resolve("games.sonic_the_hedgehog.zone");
    }

    public String character() {
        return resolve("games.sonic_the_hedgehog.character");
    }

    public String game() {
        return resolve("games.sonic_the_hedgehog.game");
    }

}
