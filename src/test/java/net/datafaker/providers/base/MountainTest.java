package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class MountainTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Mountain mountain = faker.mountain();
        return Arrays.asList(TestSpec.of(mountain::name, "mountain.name"),
                TestSpec.of(mountain::range, "mountain.range"));
    }
}
