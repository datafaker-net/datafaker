package net.datafaker.providers.sport;

import java.util.Arrays;
import java.util.Collection;

class FootballTest extends SportFakerTest {

    private final Football football = getFaker().football();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(football::coaches, "football.coaches"),
            TestSpec.of(football::competitions, "football.competitions"),
            TestSpec.of(football::players, "football.players"),
            TestSpec.of(football::positions, "football.positions"),
            TestSpec.of(football::teams, "football.teams")
        );
    }
}
