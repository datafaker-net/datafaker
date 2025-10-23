package net.datafaker.providers.entertainment;


import java.util.Collection;
import java.util.List;

class GravityFallsTest extends EntertainmentFakerTest {

    private final GravityFalls gravityFalls = getFaker().gravityFalls();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(gravityFalls::character, "gravity_falls.characters"),
            TestSpec.of(gravityFalls::location, "gravity_falls.locations"),
            TestSpec.of(gravityFalls::creature, "gravity_falls.creatures"),
            TestSpec.of(gravityFalls::artifact, "gravity_falls.artifacts"),
            TestSpec.of(gravityFalls::quote, "gravity_falls.quotes"),
            TestSpec.of(gravityFalls::mabelSweater, "gravity_falls.mabel_sweaters"),
            TestSpec.of(gravityFalls::mysteryShackItem, "gravity_falls.mystery_shack_items")
        );
    }
}
