package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class DumbAndDumberTest extends EntertainmentFakerTest {

    private final DumbAndDumber dumbAndDumber = getFaker().dumbAndDumber();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(dumbAndDumber::actor, "dumb_and_dumber.actors"),
            TestSpec.of(dumbAndDumber::character, "dumb_and_dumber.characters"),
            TestSpec.of(dumbAndDumber::quote, "dumb_and_dumber.quotes")
        );
    }
}
