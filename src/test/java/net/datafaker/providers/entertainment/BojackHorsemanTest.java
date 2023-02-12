package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class BojackHorsemanTest extends EntertainmentFakerTest {

    private final BojackHorseman bojackHorseman = getFaker().bojackHorseman();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(bojackHorseman::characters, "bojack_horseman.characters"),
            TestSpec.of(bojackHorseman::quotes, "bojack_horseman.quotes"),
            TestSpec.of(bojackHorseman::tongueTwisters, "bojack_horseman.tongue_twisters"));
    }
}
