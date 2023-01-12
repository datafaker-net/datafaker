package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Heroes of the Storm is a crossover multiplayer online battle arena video game developed and published by Blizzard Entertainment.
 *
 * @since 1.7.0
 */
public class HeroesOfTheStorm extends AbstractProvider<VideoGameProviders> {

    protected HeroesOfTheStorm(VideoGameProviders faker) {
        super(faker);
    }

    public String battleground() {
        return resolve("heroes_of_the_storm.battlegrounds");
    }

    public String heroClass() {
        return resolve("heroes_of_the_storm.classes");
    }

    public String hero() {
        return resolve("heroes_of_the_storm.heroes");
    }

    public String quote() {
        return resolve("heroes_of_the_storm.quotes");
    }

}
