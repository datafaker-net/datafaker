package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

class WorldOfWarcraftTest extends VideoGameFakerTest {

    private final WorldOfWarcraft worldOfWarcraft = getFaker().worldOfWarcraft();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(worldOfWarcraft::hero, "games.world_of_warcraft.hero"),
            TestSpec.of(worldOfWarcraft::quotes, "games.world_of_warcraft.quotes")
        );
    }
}
