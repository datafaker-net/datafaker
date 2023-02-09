package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class BrooklynNineNineTest extends EntertainmentFakerTest {

    private final BrooklynNineNine brooklynNineNine = getFaker().brooklynNineNine();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(brooklynNineNine::characters, "brooklyn_nine_nine.characters"),
            TestSpec.of(brooklynNineNine::quotes, "brooklyn_nine_nine.quotes"));
    }
}
