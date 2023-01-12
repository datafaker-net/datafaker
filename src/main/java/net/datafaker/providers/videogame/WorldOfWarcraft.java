package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * World of Warcraft is a massively multiplayer online role-playing game released in 2004 by Blizzard Entertainment.
 *
 * @since 1.8.0
 */
public class WorldOfWarcraft extends AbstractProvider<VideoGameProviders> {

    protected WorldOfWarcraft(VideoGameProviders faker) {
        super(faker);
    }

    public String hero() {
        return resolve("games.world_of_warcraft.hero");
    }

    public String quotes() {
        return resolve("games.world_of_warcraft.quotes");
    }

}
