package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class DepartedTest extends EntertainmentFakerTest {

    private final Departed departed = getFaker().departed();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(departed::actor, "departed.actors"),
            TestSpec.of(departed::character, "departed.characters"),
            TestSpec.of(departed::quote, "departed.quotes")
        );
    }
}
