package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Warhammer Fantasy is a tabletop miniature wargame with a medieval fantasy theme.
 *
 * @since 1.8.0
 */
public class WarhammerFantasy extends AbstractProvider<VideoGameProviders> {

    protected WarhammerFantasy(VideoGameProviders faker) {
        super(faker);
    }

    public String heros() {
        return resolve("games.warhammer_fantasy.heros");
    }

    public String quotes() {
        return resolve("games.warhammer_fantasy.quotes");
    }

    public String locations() {
        return resolve("games.warhammer_fantasy.locations");
    }

    public String factions() {
        return resolve("games.warhammer_fantasy.factions");
    }

    public String creatures() {
        return resolve("games.warhammer_fantasy.creatures");
    }

}
