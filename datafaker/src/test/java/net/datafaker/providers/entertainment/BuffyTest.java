package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class BuffyTest extends EntertainmentFakerTest {

    private final Buffy buffy = getFaker().buffy();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(buffy::characters, "buffy.characters"),
            TestSpec.of(buffy::quotes, "buffy.quotes"),
            TestSpec.of(buffy::celebrities, "buffy.celebrities"),
            TestSpec.of(buffy::bigBads, "buffy.big_bads"),
            TestSpec.of(buffy::episodes, "buffy.episodes")
        );
    }
}
