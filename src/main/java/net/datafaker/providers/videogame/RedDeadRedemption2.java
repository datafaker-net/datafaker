package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Red Dead Redemption 2 is an action-adventure game developed and published by Rockstar Games.
 *
 * @since 2.0.0
 */
public class RedDeadRedemption2 extends AbstractProvider<VideoGameProviders> {

    protected RedDeadRedemption2(VideoGameProviders faker) {
        super(faker);
    }

    public String protagonist() {
        return resolve("red_dead_redemption2.protagonists");
    }

    public String gangMember() {
        return resolve("red_dead_redemption2.gang_members");
    }

    public String majorCharacter() {
        return resolve("red_dead_redemption2.major_characters");
    }

    public String animal() {
        return resolve("red_dead_redemption2.animals");
    }

    public String state() {
        return resolve("red_dead_redemption2.states");
    }

    public String region() {
        return resolve("red_dead_redemption2.regions");
    }

    public String settlement() {
        return resolve("red_dead_redemption2.settlements");
    }

    public String quote() {
        return resolve("red_dead_redemption2.quotes");
    }

    public String weapon() {
        return resolve("red_dead_redemption2.weapons");
    }
}
