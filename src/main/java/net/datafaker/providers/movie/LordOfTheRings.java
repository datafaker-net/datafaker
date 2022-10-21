package net.datafaker.providers.movie;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class LordOfTheRings extends AbstractProvider<MovieProviders> {

    protected LordOfTheRings(final MovieProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("lord_of_the_rings.characters");
    }

    public String location() {
        return resolve("lord_of_the_rings.locations");
    }
}
