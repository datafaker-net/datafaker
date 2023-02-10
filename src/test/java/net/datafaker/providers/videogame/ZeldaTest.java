package net.datafaker.providers.videogame;

import java.util.Arrays;
import java.util.Collection;


class ZeldaTest extends VideoGameFakerTest {

    private final Zelda zelda = getFaker().zelda();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(zelda::character, "games.zelda.characters"),
            TestSpec.of(zelda::game, "games.zelda.games")
        );
    }
}
