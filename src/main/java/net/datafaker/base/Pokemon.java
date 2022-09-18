package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Pokemon extends AbstractProvider<IProviders> {

    protected Pokemon(BaseFaker faker) {
        super(faker);
    }

    public String name() {
        return resolve("games.pokemon.names");
    }

    public String location() {
        return resolve("games.pokemon.locations");
    }
}
