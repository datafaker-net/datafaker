package net.datafaker.providers.base;

/**
 * This class is used to generate gender randomly.
 *
 * @since 0.8.0
 */
public class Gender extends AbstractProvider<BaseProviders> {

    protected Gender(BaseProviders faker) {
        super(faker);
    }

    /**
     * This method returns a gender type
     *
     * @return a string of gender type
     */
    public String types() {
        return resolve("gender.types");
    }

    /**
     * This method returns a binary gender type
     *
     * @return a string of binary gender type
     */
    public String binaryTypes() {
        return resolve("gender.binary_types");
    }

    /**
     * This method returns a short binary gender type
     *
     * @return a string of short binary gender type
     */
    public String shortBinaryTypes() {
        return resolve("gender.short_binary_types");
    }
}
