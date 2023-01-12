package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class GameOfThrones extends AbstractProvider<ShowProviders> {

    protected GameOfThrones(ShowProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("game_of_thrones.characters");
    }

    public String house() {
        return resolve("game_of_thrones.houses");
    }

    public String city() {
        return resolve("game_of_thrones.cities");
    }

    public String dragon() {
        return resolve("game_of_thrones.dragons");
    }

    public String quote() {
        return resolve("game_of_thrones.quotes");
    }
}
