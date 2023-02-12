package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class MountaineeringTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.mountaineering().mountaineer(), "mountaineering.mountaineer"));
    }
}
