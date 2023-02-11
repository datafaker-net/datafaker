package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;


class HeyArnoldTest extends EntertainmentFakerTest {

    private final HeyArnold heyArnold = getFaker().heyArnold();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(heyArnold::characters, "hey_arnold.characters"),
            TestSpec.of(heyArnold::locations, "hey_arnold.locations"),
            TestSpec.of(heyArnold::quotes, "hey_arnold.quotes")
        );
    }
}
