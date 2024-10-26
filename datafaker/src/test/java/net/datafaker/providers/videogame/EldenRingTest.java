package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

class EldenRingTest extends VideoGameFakerTest {

    private final EldenRing eldenRing = getFaker().eldenRing();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(eldenRing::location, "elden_ring.location"),
            TestSpec.of(eldenRing::npc, "elden_ring.npc"),
            TestSpec.of(eldenRing::skill, "elden_ring.skill"),
            TestSpec.of(eldenRing::spell, "elden_ring.spell"),
            TestSpec.of(eldenRing::weapon, "elden_ring.weapon")
        );
    }
}
