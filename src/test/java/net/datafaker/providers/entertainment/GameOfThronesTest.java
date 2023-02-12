package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class GameOfThronesTest extends EntertainmentFakerTest {

    private final GameOfThrones gameOfThrones = getFaker().gameOfThrones();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(gameOfThrones::character, "game_of_thrones.characters"),
            TestSpec.of(gameOfThrones::city, "game_of_thrones.cities"),
            TestSpec.of(gameOfThrones::dragon, "game_of_thrones.dragons"),
            TestSpec.of(gameOfThrones::house, "game_of_thrones.houses"),
            TestSpec.of(gameOfThrones::quote, "game_of_thrones.quotes")
        );
    }
}
