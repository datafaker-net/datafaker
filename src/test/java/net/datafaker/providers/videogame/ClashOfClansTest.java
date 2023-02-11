package net.datafaker.providers.videogame;

import java.util.Arrays;
import java.util.Collection;


class ClashOfClansTest extends VideoGameFakerTest {

    private final ClashOfClans clashOfClans = getFaker().clashOfClans();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(clashOfClans::defensiveBuilding, "clash_of_clans.defensive_buildings"),
            TestSpec.of(clashOfClans::rank, "clash_of_clans.ranks"),
            TestSpec.of(clashOfClans::troop, "clash_of_clans.troops")
        );
    }
}
