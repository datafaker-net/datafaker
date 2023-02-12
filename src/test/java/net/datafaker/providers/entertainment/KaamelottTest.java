package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class KaamelottTest extends EntertainmentFakerTest {

    private final Kaamelott kaamelott = getFaker().kaamelott();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(kaamelott::character, "kaamelott.characters"),
            TestSpec.of(kaamelott::quote, "kaamelott.quotes")
        );
    }
}
