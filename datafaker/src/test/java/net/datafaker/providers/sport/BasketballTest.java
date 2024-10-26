package net.datafaker.providers.sport;

import java.util.List;
import java.util.Collection;

class BasketballTest extends SportFakerTest {

    private final Basketball basketball = getFaker().basketball();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(basketball::coaches, "basketball.coaches"),
            TestSpec.of(basketball::players, "basketball.players"),
            TestSpec.of(basketball::positions, "basketball.positions"),
            TestSpec.of(basketball::teams, "basketball.teams")
        );
    }
}
