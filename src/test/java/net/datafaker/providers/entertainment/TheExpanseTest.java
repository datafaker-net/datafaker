package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class TheExpanseTest extends EntertainmentFakerTest {

    private final TheExpanse theExpanse = getFaker().theExpanse();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(theExpanse::characters, "the_expanse.characters"),
            TestSpec.of(theExpanse::locations, "the_expanse.locations"),
            TestSpec.of(theExpanse::quotes, "the_expanse.quotes"),
            TestSpec.of(theExpanse::ships, "the_expanse.ships")
        );
    }
}
