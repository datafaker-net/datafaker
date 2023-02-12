package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class SpongebobTest extends EntertainmentFakerTest {

    private final Spongebob spongebob = getFaker().spongebob();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(spongebob::characters, "spongebob.characters"),
            TestSpec.of(spongebob::episodes, "spongebob.episodes"),
            TestSpec.of(spongebob::quotes, "spongebob.quotes")
        );
    }
}

