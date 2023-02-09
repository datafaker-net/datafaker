package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class StrangerThingsTest extends EntertainmentFakerTest {

    private final StrangerThings strangerThings = getFaker().strangerThings();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(strangerThings::character, "stranger_things.character"),
            TestSpec.of(strangerThings::quote, "stranger_things.quote")
        );
    }
}
