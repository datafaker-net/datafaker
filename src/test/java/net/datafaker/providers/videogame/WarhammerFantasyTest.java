package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

class WarhammerFantasyTest extends VideoGameFakerTest {

    private final WarhammerFantasy warhammerFantasy = getFaker().warhammerFantasy();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(warhammerFantasy::creatures, "games.warhammer_fantasy.creatures"),
            TestSpec.of(warhammerFantasy::factions, "games.warhammer_fantasy.factions"),
            TestSpec.of(warhammerFantasy::heros, "games.warhammer_fantasy.heros"),
            TestSpec.of(warhammerFantasy::locations, "games.warhammer_fantasy.locations"),
            TestSpec.of(warhammerFantasy::quotes, "games.warhammer_fantasy.quotes")
        );
    }
}
