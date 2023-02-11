package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;


class Babylon5Test extends EntertainmentFakerTest {

    private final Babylon5 babylon5 = getFaker().babylon5();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(babylon5::character, "babylon5.characters"),
            TestSpec.of(babylon5::quote, "babylon5.quotes"));
    }
}
