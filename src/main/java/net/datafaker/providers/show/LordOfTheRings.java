package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class LordOfTheRings extends AbstractProvider<ShowProviders> {

    protected LordOfTheRings(final ShowProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("lord_of_the_rings.characters");
    }

    public String location() {
        return resolve("lord_of_the_rings.locations");
    }
}
