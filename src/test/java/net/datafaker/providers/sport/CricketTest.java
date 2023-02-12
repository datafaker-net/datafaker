package net.datafaker.providers.sport;

import java.util.List;
import java.util.Collection;

class CricketTest extends SportFakerTest {

    private final Cricket cricket = getFaker().cricket();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(cricket::formats, "cricket.formats"),
            TestSpec.of(cricket::players, "cricket.players"),
            TestSpec.of(cricket::teams, "cricket.teams"),
            TestSpec.of(cricket::tournaments, "cricket.tournaments")
        );
    }
}
