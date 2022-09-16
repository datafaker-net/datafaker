package net.datafaker;

/**
 * @since 0.8.0
 */
public class Zelda extends AbstractProvider {

    protected Zelda(final Faker faker) {
        super(faker);
    }

    public String game() {
        return resolve("games.zelda.games");
    }

    public String character() {
        return resolve("games.zelda.characters");
    }
}
