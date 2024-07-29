package net.datafaker.providers.videogame;

import java.util.Collection;
import java.util.List;

class Dota2Test extends VideoGameFakerTest {
    private final Dota2 dota2 = getFaker().dota2();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(dota2::faction, "games.dota2.faction"),
            TestSpec.of(dota2::rank, "games.dota2.rank"),
            TestSpec.of(dota2::attribute, "games.dota2.attribute"),
            TestSpec.of(dota2::building, "games.dota2.building"),
            TestSpec.of(dota2::hero, "games.dota2.hero"),
            TestSpec.of(dota2::item, "games.dota2.item"),
            TestSpec.of(dota2::team, "games.dota2.team"),
            TestSpec.of(dota2::player, "games.dota2.player")
        );
    }
}
