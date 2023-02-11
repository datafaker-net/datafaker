package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class DepartedTest extends EntertainmentFakerTest {

    private final Departed departed = getFaker().departed();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(departed::actor, "departed.actors"),
            TestSpec.of(departed::character, "departed.characters"),
            TestSpec.of(departed::quote, "departed.quotes")
        );
    }
}
