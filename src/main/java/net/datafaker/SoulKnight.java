package net.datafaker;


/**
 * A class for generating random value of soul Knight
 *
 * @author zhou mintao
 * @since 1.4.0
 */
public class SoulKnight {
    private final Faker faker;

    protected SoulKnight(final Faker faker) {
        this.faker = faker;
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
