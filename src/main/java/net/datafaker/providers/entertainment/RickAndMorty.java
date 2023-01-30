package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class RickAndMorty extends AbstractProvider<EntertainmentProviders> {

    protected RickAndMorty(final EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("rick_and_morty.characters");
    }

    public String location() {
        return resolve("rick_and_morty.locations");
    }

    public String quote() {
        return resolve("rick_and_morty.quotes");
    }
}
