package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class KaamelottTest extends EntertainmentFakerTest {

    private final Kaamelott kaamelott = getFaker().kaamelott();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(kaamelott::character, "kaamelott.characters"),
            TestSpec.of(kaamelott::quote, "kaamelott.quotes")
        );
    }
}
