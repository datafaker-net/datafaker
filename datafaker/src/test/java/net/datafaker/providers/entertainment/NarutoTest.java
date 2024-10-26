package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class NarutoTest extends EntertainmentFakerTest {

    private final Naruto naruto = getFaker().naruto();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(naruto::character, "naruto.characters"),
            TestSpec.of(naruto::demon, "naruto.demons"),
            TestSpec.of(naruto::eye, "naruto.eyes"),
            TestSpec.of(naruto::village, "naruto.villages")
        );
    }
}
