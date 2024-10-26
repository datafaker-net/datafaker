package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Elden Ring is a 2022 action role-playing game developed by FromSoftware and published by Bandai Namco Entertainment.
 *
 * @since 1.4.0
 */
public class EldenRing extends AbstractProvider<VideoGameProviders> {

    protected EldenRing(VideoGameProviders faker) {
        super(faker);
    }

    public String location() {
        return resolve("elden_ring.location");
    }

    public String weapon() {
        return resolve("elden_ring.weapon");
    }

    public String skill() {
        return resolve("elden_ring.skill");
    }

    public String spell() {
        return resolve("elden_ring.spell");
    }

    public String npc() {
        return resolve("elden_ring.npc");
    }
}
