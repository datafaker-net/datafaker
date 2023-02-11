package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class GratefulDeadTest extends EntertainmentFakerTest {

    private final GratefulDead gratefulDead = getFaker().gratefulDead();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(gratefulDead::players, "grateful_dead.players"),
            TestSpec.of(gratefulDead::songs, "grateful_dead.songs")
        );
    }
}
