package net.datafaker.providers.sport;

import java.util.List;
import java.util.Collection;

public class BaseballTest extends SportFakerTest {

    private final Baseball baseball = getFaker().baseball();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(baseball::coaches, "baseball.coaches"),
            TestSpec.of(baseball::players, "baseball.players"),
            TestSpec.of(baseball::positions, "baseball.positions"),
            TestSpec.of(baseball::teams, "baseball.teams")
        );
    }
}
