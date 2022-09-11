package net.datafaker;


/**
 * A class for generating random values for Soul Knight.
 *
 * @author zhou mintao
 * @since 1.4.0
 */
public class SoulKnight extends AbstractProvider<VideoGameFaker> {

    protected SoulKnight(final VideoGameFaker faker) {
        super(faker);
    }

    /**
     * @return a random value of characters
     */
    public String characters() {
        return faker.resolve("soul_knight.characters");
    }

    /**
     * @return a random value of buffs
     */
    public String buffs() {
        return faker.resolve("soul_knight.buffs");
    }

    /**
     * @return a random value of statues
     */
    public String statues() {
        return faker.resolve("soul_knight.statues");
    }

    /**
     * @return a random value of weapons
     */
    public String weapons() {
        return faker.resolve("soul_knight.weapons");
    }

    /**
     * @return a random value of bosses
     */
    public String bosses() {
        return faker.resolve("soul_knight.bosses");
    }

    /**
     * @return a random value of enemies
     */
    public String enemies() {
        return faker.resolve("soul_knight.enemies");
    }

}
