package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class TheItCrowdTest extends EntertainmentFakerTest {

    private final TheItCrowd theItCrowd = getFaker().theItCrowd();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(theItCrowd::actors, "the_it_crowd.actors"),
            TestSpec.of(theItCrowd::characters, "the_it_crowd.characters"),
            TestSpec.of(theItCrowd::emails, "the_it_crowd.emails"),
            TestSpec.of(theItCrowd::quotes, "the_it_crowd.quotes")
        );
    }
}
