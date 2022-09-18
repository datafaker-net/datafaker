package net.datafaker.base;

/**
 * @since 1.3.0
 */
public class SuperMario extends AbstractProvider<IProviders> {

    protected SuperMario(BaseFaker faker) {
        super(faker);
    }

    public String characters() {
        return resolve("games.super_mario.characters");
    }

    public String games() {
        return resolve("games.super_mario.games");
    }

    public String locations() {
        return resolve("games.super_mario.locations");
    }

}
