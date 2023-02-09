package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

public class GhostbustersTest extends EntertainmentFakerTest {

    private final Ghostbusters ghostbusters = getFaker().ghostbusters();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(ghostbusters::actor, "ghostbusters.actors"),
            TestSpec.of(ghostbusters::character, "ghostbusters.characters"),
            TestSpec.of(ghostbusters::quote, "ghostbusters.quotes")
        );
    }
}
