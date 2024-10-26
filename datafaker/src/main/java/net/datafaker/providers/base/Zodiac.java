package net.datafaker.providers.base;

/**
 * This class is used to generate Zodiac signs randomly.
 *
 * @since 1.8.0
 */

public class Zodiac extends AbstractProvider<BaseProviders> {

    protected Zodiac(BaseProviders faker) {
        super(faker);
    }

    /**
     * This method returns a Zodiac sign
     *
     * @return a string of Zodiac sign
     */
    public String sign() {
        return resolve("zodiac.signs");
    }
}
