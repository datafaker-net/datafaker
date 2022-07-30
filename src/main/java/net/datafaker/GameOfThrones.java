package net.datafaker;

/**
 * @since 0.8.0
 */
public class GameOfThrones extends AbstractProvider {

    protected GameOfThrones(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.resolve("game_of_thrones.characters");
    }

    public String house() {
        return faker.resolve("game_of_thrones.houses");
    }

    public String city() {
        return faker.resolve("game_of_thrones.cities");
    }

    public String dragon() {
        return faker.resolve("game_of_thrones.dragons");
    }

    public String quote() {
        return faker.resolve("game_of_thrones.quotes");
    }
}
