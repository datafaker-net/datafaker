package net.datafaker;

/**
 * A class for generating random value of Battlefield1 contents.
 *
 * @author coolestjj
 * @since 1.4.0
 */
public class Battlefield1 extends AbstractProvider<VideoGameFaker> {

    protected Battlefield1(final VideoGameFaker faker) {
        super(faker);
    }

    /**
     * @return a random unit class name as a string value
     */
    public String classes() {
        return faker.resolve("battlefield1.classes");
    }

    /**
     * @return a random weapon name as a string value
     */
    public String weapon() {
        return faker.resolve("battlefield1.weapon");
    }

    /**
     * @return a random vehicle name as a string value
     */
    public String vehicle() {
        return faker.resolve("battlefield1.vehicle");
    }

    /**
     * @return a random map title as a string value
     */
    public String map() {
        return faker.resolve("battlefield1.map");
    }

    /**
     * @return a random faction name as a string value
     */
    public String faction() {
        return faker.resolve("battlefield1.faction");
    }
}
