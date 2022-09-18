package net.datafaker.base;

/**
 * @since 0.9.0
 */
public class Hearthstone extends AbstractProvider<IProviders> {

    protected Hearthstone(final BaseFaker faker) {
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
        if (rank.equals("Legend")) {
            rank = rank + " " + faker.random().nextInt(1, 65000);
        } else {
            rank = rank + " " + faker.random().nextInt(1, 10);
        }
        return rank;
    }

    public String wildRank() {
        return standardRank();
    }
}
