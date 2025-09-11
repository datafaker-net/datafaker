package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Hearthstone is a free-to-play online digital collectible card game developed and published by Blizzard Entertainment.
 *
 * @since 0.9.0
 */
public class Hearthstone extends AbstractProvider<VideoGameProviders> {

    protected Hearthstone(final VideoGameProviders faker) {
        super(faker);
    }

    public String mainProfession() {
        return resolve("games.hearthstone.professions");
    }

    public String mainCharacter() {
        return resolve("games.hearthstone.characters");
    }

    public String mainPattern() {
        return resolve("games.hearthstone.patterns");
    }

    public int battlegroundsScore() {
        return faker.random().nextInt(0, 16000);
    }

    public String standardRank() {
        String rank = resolve("games.hearthstone.rank");
        return "Legend".equals(rank) ?
            rank + " " + faker.random().nextInt(1, 65000) :
            rank + " " + faker.random().nextInt(1, 10);
    }

    public String wildRank() {
        return standardRank();
    }
}
