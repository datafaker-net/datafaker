package net.datafaker.providers.videogame;


import net.datafaker.providers.base.AbstractProvider;

/**
 * Soul Knight is a game made by ChillyRoom Inc.
 *
 * @author zhou mintao
 * @since 1.4.0
 */
public class SoulKnight extends AbstractProvider<VideoGameProviders> {

    protected SoulKnight(final VideoGameProviders faker) {
        super(faker);
    }

    /**
     * @return a random value of characters
     */
    public String characters() {
        return resolve("soul_knight.characters");
    }

    /**
     * @return a random value of buffs
     */
    public String buffs() {
        return resolve("soul_knight.buffs");
    }

    /**
     * @return a random value of statues
     */
    public String statues() {
        return resolve("soul_knight.statues");
    }

    /**
     * @return a random value of weapons
     */
    public String weapons() {
        return resolve("soul_knight.weapons");
    }

    /**
     * @return a random value of bosses
     */
    public String bosses() {
        return resolve("soul_knight.bosses");
    }

    /**
     * @return a random value of enemies
     */
    public String enemies() {
        return resolve("soul_knight.enemies");
    }

}
