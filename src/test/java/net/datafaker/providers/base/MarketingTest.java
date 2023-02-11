package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class MarketingTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.marketing().buzzwords(), "marketing.buzzwords"));
    }

}
