package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;


class EsportsTest extends VideoGameFakerTest {

    private final Esports esports = getFaker().esports();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(esports::event, "esport.events"),
            TestSpec.of(esports::game, "esport.games"),
            TestSpec.of(esports::league, "esport.leagues"),
            TestSpec.of(esports::player, "esport.players"),
            TestSpec.of(esports::team, "esport.teams")
        );
    }
}
