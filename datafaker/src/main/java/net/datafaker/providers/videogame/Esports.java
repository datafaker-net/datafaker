package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Esports, short for electronic sports, is a form of competition using video games.
 *
 * @since 0.8.0
 */
public class Esports extends AbstractProvider<VideoGameProviders> {

    protected Esports(final VideoGameProviders faker) {
        super(faker);
    }

    public String player() {
        return resolve("esport.players");
    }

    public String team() {
        return resolve("esport.teams");
    }

    public String event() {
        return resolve("esport.events");
    }

    public String league() {
        return resolve("esport.leagues");
    }

    public String game() {
        return resolve("esport.games");
    }
}
