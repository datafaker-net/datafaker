package net.datafaker.providers.videogame;

import java.util.Arrays;
import java.util.Collection;


class SuperMarioTest extends VideoGameFakerTest {

    private final SuperMario superMario = getFaker().superMario();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(superMario::characters, "games.super_mario.characters"),
            TestSpec.of(superMario::games, "games.super_mario.games"),
            TestSpec.of(superMario::locations, "games.super_mario.locations")
        );
    }
}
