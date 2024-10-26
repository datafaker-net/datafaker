package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Final Fantasy XIV is an MMORPG and features a persistent world in which players can interact with each other and the environment.
 * Players create and customize their characters for use in the game, including name, race, gender, facial features, and starting class.
 *
 * @since 2.0.0
 */
public class FinalFantasyXIV extends AbstractProvider<VideoGameProviders> {

    protected FinalFantasyXIV(VideoGameProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("final_fantasy_xiv.characters");
    }

    public String job() {
        return resolve("final_fantasy_xiv.jobs");
    }

    public String race() {
        return resolve("final_fantasy_xiv.races");
    }

    public String dataCenter() {
        return resolve("final_fantasy_xiv.data_centers");
    }

    public String zone() {
        return resolve("final_fantasy_xiv.zones");
    }

}
