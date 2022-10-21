package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.6.0
 */
public class ClashOfClans extends AbstractProvider<VideoGameProviders> {

    protected ClashOfClans(VideoGameProviders faker) {
        super(faker);
    }

    public String troop() {
        return resolve("clash_of_clans.troops");
    }

    public String rank() {
        return resolve("clash_of_clans.ranks");
    }

    public String defensiveBuilding() {
        return resolve("clash_of_clans.defensive_buildings");
    }
}
