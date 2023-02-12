package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

class SuperSmashBrosTest extends VideoGameFakerTest {

    private final SuperSmashBros superSmashBros = getFaker().superSmashBros();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(superSmashBros::fighter, "games.super_smash_bros.fighter"),
            TestSpec.of(superSmashBros::stage, "games.super_smash_bros.stage")
        );
    }
}
