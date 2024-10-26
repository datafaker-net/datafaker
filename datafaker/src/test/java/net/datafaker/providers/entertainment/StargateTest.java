package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class StargateTest extends EntertainmentFakerTest {

    private final Stargate stargate = getFaker().stargate();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(stargate::characters, "stargate.characters"),
            TestSpec.of(stargate::planets, "stargate.planets"),
            TestSpec.of(stargate::quotes, "stargate.quotes")
        );
    }
}

