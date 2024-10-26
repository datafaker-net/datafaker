package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;


class ZeldaTest extends VideoGameFakerTest {

    private final Zelda zelda = getFaker().zelda();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(zelda::character, "games.zelda.characters"),
            TestSpec.of(zelda::game, "games.zelda.games")
        );
    }
}
