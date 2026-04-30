package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

class TormentaTest extends VideoGameFakerTest {

    private final Tormenta tormenta = getFaker().tormenta();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(tormenta::bestiary, "games.tormenta.bestiary"),
            TestSpec.of(tormenta::names, "games.tormenta.names"),
            TestSpec.of(tormenta::cities, "games.tormenta.cities")
        );
    }
}
