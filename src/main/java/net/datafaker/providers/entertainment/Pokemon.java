package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Pokemon extends AbstractProvider<EntertainmentProviders> {

    protected Pokemon(EntertainmentProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("games.pokemon.names");
    }

    public String location() {
        return resolve("games.pokemon.locations");
    }

    public String move() {
        return resolve("games.pokemon.moves");
    }

    public String type() {
        return resolve("games.pokemon.types");
    }
}
