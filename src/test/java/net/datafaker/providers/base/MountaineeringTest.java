package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class MountaineeringTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Mountaineering mountaineering = faker.mountaineering();
        return List.of(TestSpec.of(mountaineering::mountaineer, "mountaineering.mountaineer"));
    }
}
