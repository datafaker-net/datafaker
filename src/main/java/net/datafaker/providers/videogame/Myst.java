package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Myst is a graphic adventure/puzzle video game designed by the Miller brothers, Robyn and Rand.
 *
 * @since 1.8.0
 */
public class Myst extends AbstractProvider<VideoGameProviders> {

    protected Myst(VideoGameProviders faker) {
        super(faker);
    }

    public String games() {
        return resolve("games.myst.games");
    }

    public String creatures() {
        return resolve("games.myst.creatures");
    }

    public String characters() {
        return resolve("games.myst.characters");
    }

    public String ages() {
        return resolve("games.myst.ages");
    }

    public String quotes() {
        return resolve("games.myst.quotes");
    }

}
