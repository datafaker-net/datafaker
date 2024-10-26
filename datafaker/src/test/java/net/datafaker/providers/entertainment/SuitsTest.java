package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class SuitsTest extends EntertainmentFakerTest {

    private final Suits suits = getFaker().suits();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(suits::characters, "suits.characters"),
            TestSpec.of(suits::quotes, "suits.quotes")
        );
    }
}
