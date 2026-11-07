package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class DomainListTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(() -> faker.resolve("domain.top"), "domain.top"),
            TestSpec.of(() -> faker.resolve("domain.country"), "domain.country"),
            TestSpec.of(() -> faker.resolve("domain.prefix"), "domain.prefix"));
    }
}
