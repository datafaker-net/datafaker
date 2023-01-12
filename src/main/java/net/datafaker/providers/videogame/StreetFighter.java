package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Street Fighter is a Japanese media franchise centered on a series of fighting video and arcade games developed and published by Capcom.
 *
 * @since 1.8.0
 */
public class StreetFighter extends AbstractProvider<VideoGameProviders> {

    protected StreetFighter(VideoGameProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("games.street_fighter.characters");
    }

    public String stages() {
        return resolve("games.street_fighter.stages");
    }

    public String quotes() {
        return resolve("games.street_fighter.quotes");
    }

    public String moves() {
        return resolve("games.street_fighter.moves");
    }

}
