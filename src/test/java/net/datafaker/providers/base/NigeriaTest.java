package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class NigeriaTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.nigeria().places(), "nigeria.places"),
                TestSpec.of(() -> faker.nigeria().food(), "nigeria.food"),
                TestSpec.of(() -> faker.nigeria().name(), "nigeria.name"),
                TestSpec.of(() -> faker.nigeria().schools(), "nigeria.schools"),
                TestSpec.of(() -> faker.nigeria().celebrities(), "nigeria.celebrities"));
    }
}
