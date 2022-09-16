package net.datafaker;

/**
 * Generate random parts in BojackHorseman.
 *
 * @author unknown and irakatz
 * @since 0.8.0
 */
public class BojackHorseman extends AbstractProvider {

    /**
     * Create a constructor for BojackHorseman.
     *
     * @param faker The Faker instance for generating random parts in BojackHorseman.
     */
    protected BojackHorseman(Faker faker) {
        super(faker);
    }

    /**
     * Generate random character's name in BojackHorseman.
     *
     * @return Characters in BojackHorseman
     */
    public String characters() {
        return resolve("bojack_horseman.characters");
    }

    /**
     * Generate random quotes in BojackHorseman.
     *
     * @return Quotes in BojackHorseman
     */
    public String quotes() {
        return resolve("bojack_horseman.quotes");
    }

    /**
     * Generate random tongue twisters in BojackHorseman.
     *
     * @return Tongue twisters in BojackHorseman
     */
    public String tongueTwisters() {
        return resolve("bojack_horseman.tongue_twisters");
    }

}
