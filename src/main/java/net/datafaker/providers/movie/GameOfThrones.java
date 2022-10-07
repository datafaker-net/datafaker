package net.datafaker.providers.movie;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class GameOfThrones extends AbstractProvider<MovieProviders> {

    protected GameOfThrones(MovieProviders faker) {
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
