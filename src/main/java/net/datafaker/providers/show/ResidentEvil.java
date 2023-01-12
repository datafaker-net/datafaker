package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * A class for generating random value of ResidentEvil series.
 *
 * @since 0.9.0
 */
public class ResidentEvil extends AbstractProvider<ShowProviders> {

    protected ResidentEvil(ShowProviders faker) {
        super(faker);
    }

    /**
     * @return A random character string (like leon kennedy) of ResidentEvil series.
     */
    public String character() {
        return resolve("games.resident_evil.characters");
    }

    /**
     * @return A random biologicalAgent string of ResidentEvil series. This string may contains special characters.
     */
    public String biologicalAgent() {
        return resolve("games.resident_evil.biological-agents");
    }

    /**
     * @return A random equipment string of ResidentEvil series, which includes weapons and other items.
     */
    public String equipment() {
        return resolve("games.resident_evil.equipments");
    }

    /**
     * @return A random location string of ResidentEvil series.
     */
    public String location() {
        return resolve("games.resident_evil.locations");
    }

    /**
     * @return A random creature string of ResidentEvil series.
     */
    public String creature() {
        return resolve("games.resident_evil.creatures");
    }
}
