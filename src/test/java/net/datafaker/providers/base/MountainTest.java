package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class MountainTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Mountain mountain = faker.mountain();
        return List.of(TestSpec.of(mountain::name, "mountain.name"),
                TestSpec.of(mountain::range, "mountain.range"));
    }
}
