package net.datafaker.providers.movie;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Pokemon extends AbstractProvider<MovieProviders> {

    protected Pokemon(MovieProviders faker) {
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
}
