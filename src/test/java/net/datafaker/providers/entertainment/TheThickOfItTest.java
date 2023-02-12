package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class TheThickOfItTest extends EntertainmentFakerTest {

    private final TheThickOfIt theThickOfIt = getFaker().theThickOfIt();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(theThickOfIt::characters, "the_thick_of_it.characters"),
            TestSpec.of(theThickOfIt::departments, "the_thick_of_it.departments"),
            TestSpec.of(theThickOfIt::positions, "the_thick_of_it.positions")
        );
    }
}
