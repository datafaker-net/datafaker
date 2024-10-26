package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;


class OverwatchTest extends VideoGameFakerTest {

    private final Overwatch overwatch = getFaker().overwatch();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(overwatch::hero, "games.overwatch.heroes"),
            TestSpec.of(overwatch::location, "games.overwatch.locations"),
            TestSpec.of(overwatch::quote, "games.overwatch.quotes")
        );
    }
}
