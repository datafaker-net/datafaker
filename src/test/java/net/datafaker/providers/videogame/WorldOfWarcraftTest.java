package net.datafaker.providers.videogame;

import java.util.Arrays;
import java.util.Collection;

class WorldOfWarcraftTest extends VideoGameFakerTest {

    private final WorldOfWarcraft worldOfWarcraft = getFaker().worldOfWarcraft();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(worldOfWarcraft::hero, "games.world_of_warcraft.hero"),
            TestSpec.of(worldOfWarcraft::quotes, "games.world_of_warcraft.quotes")
        );
    }
}
