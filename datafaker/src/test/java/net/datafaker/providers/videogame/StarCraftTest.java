package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

class StarCraftTest extends VideoGameFakerTest {

    private final StarCraft starCraft = getFaker().starCraft();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(starCraft::building, "starcraft.buildings"),
            TestSpec.of(starCraft::character, "starcraft.characters"),
            TestSpec.of(starCraft::planet, "starcraft.planets"),
            TestSpec.of(starCraft::unit, "starcraft.units")
        );
    }
}
