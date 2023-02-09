package net.datafaker.providers.videogame;

import java.util.Arrays;
import java.util.Collection;

class StreetFighterTest extends VideoGameFakerTest {

    private final StreetFighter streetFighter = getFaker().streetFighter();
    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(streetFighter::characters, "games.street_fighter.characters"),
            TestSpec.of(streetFighter::moves, "games.street_fighter.moves"),
            TestSpec.of(streetFighter::stages, "games.street_fighter.stages"),
            TestSpec.of(streetFighter::quotes, "games.street_fighter.quotes")
        );
    }
}
