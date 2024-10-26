package net.datafaker.providers.videogame;

import java.util.Collection;
import java.util.List;

public class FinalFantasyXIVTest extends VideoGameFakerTest {

    private final FinalFantasyXIV finalFantasyXIV = getFaker().finalFantasyXIV();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(finalFantasyXIV::character, "final_fantasy_xiv.characters"),
            TestSpec.of(finalFantasyXIV::job, "final_fantasy_xiv.jobs"),
            TestSpec.of(finalFantasyXIV::race, "final_fantasy_xiv.races"),
            TestSpec.of(finalFantasyXIV::dataCenter, "final_fantasy_xiv.data_centers"),
            TestSpec.of(finalFantasyXIV::zone, "final_fantasy_xiv.zones")
        );
    }
}
