package net.datafaker;

/**
 * @since 0.8.0
 */
public class Zelda extends AbstractProvider<VideoGameFaker> {

    protected Zelda(final VideoGameFaker faker) {
        super(faker);
    }

    public String game() {
        return faker.resolve("games.zelda.games");
    }

    public String character() {
        return faker.resolve("games.zelda.characters");
    }
}
