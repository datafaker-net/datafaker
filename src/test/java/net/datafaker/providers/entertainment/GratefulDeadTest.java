package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.GratefulDead;

import java.util.Arrays;
import java.util.Collection;

class GratefulDeadTest extends EntertainmentFakerTest {

    private final GratefulDead gratefulDead = getFaker().gratefulDead();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(gratefulDead::players, "grateful_dead.players"),
            TestSpec.of(gratefulDead::songs, "grateful_dead.songs")
        );
    }
}
