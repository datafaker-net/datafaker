package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
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
