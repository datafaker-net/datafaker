package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class RuPaulDragRaceTest extends EntertainmentFakerTest {

    private final RuPaulDragRace ruPaulDragRace = getFaker().ruPaulDragRace();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(ruPaulDragRace::queen, "rupaul.queens"),
            TestSpec.of(ruPaulDragRace::quote, "rupaul.quotes")
        );
    }
}
