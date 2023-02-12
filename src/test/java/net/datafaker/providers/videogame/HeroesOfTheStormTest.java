package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

public class HeroesOfTheStormTest extends VideoGameFakerTest {

    private final HeroesOfTheStorm heroesOfTheStorm = getFaker().heroesOfTheStorm();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(heroesOfTheStorm::battleground, "heroes_of_the_storm.battlegrounds"),
            TestSpec.of(heroesOfTheStorm::hero, "heroes_of_the_storm.heroes"),
            TestSpec.of(heroesOfTheStorm::heroClass, "heroes_of_the_storm.classes"),
            TestSpec.of(heroesOfTheStorm::quote, "heroes_of_the_storm.quotes")
        );
    }
}
