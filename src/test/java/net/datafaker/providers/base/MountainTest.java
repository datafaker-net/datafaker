package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class MountainTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.mountain().name(), "mountain.name"),
                TestSpec.of(() -> faker.mountain().range(), "mountain.range"));
    }
}
