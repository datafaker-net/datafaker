package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class SeinfeldTest extends EntertainmentFakerTest {

    private final Seinfeld seinfeld = getFaker().seinfeld();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(seinfeld::business, "seinfeld.business"),
            TestSpec.of(seinfeld::character, "seinfeld.character"),
            TestSpec.of(seinfeld::quote, "seinfeld.quote")
        );
    }
}
