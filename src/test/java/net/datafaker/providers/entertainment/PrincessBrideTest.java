package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class PrincessBrideTest extends EntertainmentFakerTest {

    private final PrincessBride princessBride = getFaker().princessBride();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(princessBride::character, "princess_bride.characters"),
            TestSpec.of(princessBride::quote, "princess_bride.quotes")
        );
    }
}
