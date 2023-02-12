package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

class HalfLifeTest extends VideoGameFakerTest {

    private final HalfLife halfLife = getFaker().halfLife();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(halfLife::character, "games.half_life.character"),
            TestSpec.of(halfLife::enemy, "games.half_life.enemy"),
            TestSpec.of(halfLife::location, "games.half_life.location")
        );
    }
}
