package net.datafaker.providers.sport;

import java.util.Arrays;
import java.util.Collection;

class VolleyballTest extends SportFakerTest {

    private final Volleyball volleyball = getFaker().volleyball();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(volleyball::coach, "volleyball.coach"),
            TestSpec.of(volleyball::formation, "volleyball.formation"),
            TestSpec.of(volleyball::player, "volleyball.player"),
            TestSpec.of(volleyball::position, "volleyball.position"),
            TestSpec.of(volleyball::team, "volleyball.team")
        );
    }
}
