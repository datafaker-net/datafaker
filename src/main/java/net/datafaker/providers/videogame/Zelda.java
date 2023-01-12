package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * The Legend of Zelda is an action-adventure game franchise created by the Japanese game designers Shigeru Miyamoto and Takashi Tezuka.
 *
 * @since 0.8.0
 */
public class Zelda extends AbstractProvider<VideoGameProviders> {

    protected Zelda(final VideoGameProviders faker) {
        super(faker);
    }

    public String game() {
        return resolve("games.zelda.games");
    }

    public String character() {
        return resolve("games.zelda.characters");
    }
}
