package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class TheVentureBrosTest extends EntertainmentFakerTest {

    private final TheVentureBros theVentureBros = getFaker().theVentureBros();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(theVentureBros::character, "the_venture_bros.character"),
            TestSpec.of(theVentureBros::organization, "the_venture_bros.organization"),
            TestSpec.of(theVentureBros::quote, "the_venture_bros.quote"),
            TestSpec.of(theVentureBros::vehicle, "the_venture_bros.vehicle")
        );
    }
}
