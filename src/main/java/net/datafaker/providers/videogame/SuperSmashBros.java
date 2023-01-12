package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Super Smash Bros. is a crossover fighting game series published by Nintendo.
 *
 * @since 1.8.0
 */
public class SuperSmashBros extends AbstractProvider<VideoGameProviders> {

    protected SuperSmashBros(VideoGameProviders faker) {
        super(faker);
    }

    public String fighter() {
        return resolve("games.super_smash_bros.fighter");
    }

    public String stage() {
        return resolve("games.super_smash_bros.stage");
    }

}
