package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

class MystTest extends VideoGameFakerTest {

    private final Myst myst = getFaker().myst();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(myst::ages, "games.myst.ages"),
            TestSpec.of(myst::characters, "games.myst.characters"),
            TestSpec.of(myst::creatures, "games.myst.creatures"),
            TestSpec.of(myst::games, "games.myst.games"),
            TestSpec.of(myst::quotes, "games.myst.quotes")
        );
    }
}
