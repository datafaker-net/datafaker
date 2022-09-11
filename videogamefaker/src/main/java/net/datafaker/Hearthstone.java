package net.datafaker;

/**
 * @since 0.9.0
 */
public class Hearthstone extends AbstractProvider<VideoGameFaker> {

    protected Hearthstone(final VideoGameFaker faker) {
        super(faker);
    }

    public String mainProfession() {
        return faker.resolve("games.hearthstone.professions");
    }

    public String mainCharacter() {
        return faker.fakeValuesService().resolve("games.hearthstone.characters", this);
    }

    public String mainPattern() {
        return faker.resolve("games.hearthstone.patterns");
    }

    public int battlegroundsScore() {
        return faker.random().nextInt(0, 16000);
    }

    public String standardRank() {
        String rank = faker.resolve("games.hearthstone.rank");
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
