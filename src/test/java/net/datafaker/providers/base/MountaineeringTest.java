package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class MountaineeringTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.mountaineering().mountaineer(), "mountaineering.mountaineer"));
    }
}
