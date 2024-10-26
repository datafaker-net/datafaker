package net.datafaker.providers.sport;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.8.0
 */
public class Chess extends AbstractProvider<SportProviders> {

    protected Chess(SportProviders faker) {
        super(faker);
    }

    public String player() {
        return resolve("chess.players");
    }

    public String tournament() {
        return resolve("chess.tournaments");
    }

    public String opening() {
        return resolve("chess.openings");
    }

    public String title() {
        return resolve("chess.titles");
    }

}
