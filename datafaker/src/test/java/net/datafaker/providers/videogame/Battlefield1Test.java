package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;


class Battlefield1Test extends VideoGameFakerTest {

    private final Battlefield1 battlefield1 = getFaker().battlefield1();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(battlefield1::classes, "battlefield1.classes"),
            TestSpec.of(battlefield1::faction, "battlefield1.faction"),
            TestSpec.of(battlefield1::map, "battlefield1.map"),
            TestSpec.of(battlefield1::vehicle, "battlefield1.vehicle"),
            TestSpec.of(battlefield1::weapon, "battlefield1.weapon")
        );
    }
}
