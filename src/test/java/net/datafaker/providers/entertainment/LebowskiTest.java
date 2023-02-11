package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;


class LebowskiTest extends EntertainmentFakerTest {

    private final Lebowski lebowski = getFaker().lebowski();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(lebowski::actor, "lebowski.actors"),
            TestSpec.of(lebowski::character, "lebowski.characters"),
            TestSpec.of(lebowski::quote, "lebowski.quotes")
        );
    }
}
