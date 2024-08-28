package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Dota 2 is a multiplayer online battle arena (MOBA) game developed and published by Valve Corporation.
 *
 * @author panic08
 * @since 2.3.1
 */
public class Dota2 extends AbstractProvider<VideoGameProviders> {

    protected Dota2(VideoGameProviders faker) {
        super(faker);
    }

    public String faction() {
        return resolve("games.dota2.faction");
    }

    public String rank() {
        return resolve("games.dota2.rank");
    }

    public String attribute() {
        return resolve("games.dota2.attribute");
    }

    public String building() {
        return resolve("games.dota2.building");
    }

    public String hero() {
        return resolve("games.dota2.hero");
    }

    public String heroQuote(String heroName) {
        return resolve("games.dota2." + heroName + ".quote");
    }

    public String item() {
        return resolve("games.dota2.item");
    }

    public String neutralItem() {
        return resolve("games.dota2.neutral_item");
    }

    public String team() {
        return resolve("games.dota2.team");
    }

    public String tier() {
        return resolve("games.dota2.tier");
    }

    public String player() {
        return resolve("games.dota2.player");
    }
}
