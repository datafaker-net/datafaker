package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class StarTrek extends AbstractProvider<EntertainmentProviders> {

    protected StarTrek(EntertainmentProviders faker) {
        super(faker);
    }

    /** @return a random Star Trek character */
    public String character() {
        return resolve("star_trek.character");
    }

    /** @return a random Star Trek location (planet, starbase, area of space) */
    public String location() {
        return resolve("star_trek.location");
    }

    /** @return a random alien species */
    public String species() {
        return resolve("star_trek.species");
    }

    /** @return a random Star Trek villain */
    public String villain() {
        return resolve("star_trek.villain");
    }

    /** @return a random Klingon phrase */
    public String klingon() {
        return resolve("star_trek.klingon");
    }

    /** @return a random Federation starship */
    public String starship() {
        return resolve("star_trek.starship");
    }
}
