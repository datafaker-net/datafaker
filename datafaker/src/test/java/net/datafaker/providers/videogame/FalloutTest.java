package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;


public class FalloutTest extends VideoGameFakerTest {

    private final Fallout fallout = getFaker().fallout();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(fallout::character, "fallout.characters"),
            TestSpec.of(fallout::faction, "fallout.factions"),
            TestSpec.of(fallout::location, "fallout.locations"),
            TestSpec.of(fallout::quote, "fallout.quotes")
        );
    }
}
