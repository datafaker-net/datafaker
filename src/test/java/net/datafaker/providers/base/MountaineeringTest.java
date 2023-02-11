package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class MountaineeringTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Mountaineering mountaineering = faker.mountaineering();
        return Arrays.asList(TestSpec.of(mountaineering::mountaineer, "mountaineering.mountaineer"));
    }
}
