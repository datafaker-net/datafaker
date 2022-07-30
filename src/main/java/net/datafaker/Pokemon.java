package net.datafaker;

/**
 * @since 0.8.0
 */
public class Pokemon extends AbstractProvider {

    protected Pokemon(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.resolve("games.pokemon.names");
    }

    public String location() {
        return faker.resolve("games.pokemon.locations");
    }
}
