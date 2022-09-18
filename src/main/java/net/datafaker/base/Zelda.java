package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Zelda extends AbstractProvider<IProviders> {

    protected Zelda(final BaseFaker faker) {
        super(faker);
    }

    public String game() {
        return resolve("games.zelda.games");
    }

    public String character() {
        return resolve("games.zelda.characters");
    }
}
