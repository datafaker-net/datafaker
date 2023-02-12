package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;


class FinalSpaceTest extends EntertainmentFakerTest {

    private final FinalSpace finalSpace = getFaker().finalSpace();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(finalSpace::character, "final_space.characters"),
            TestSpec.of(finalSpace::quote, "final_space.quotes"),
            TestSpec.of(finalSpace::vehicle, "final_space.vehicles")
        );
    }
}
