package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class MarketingTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.marketing().buzzwords(), "marketing.buzzwords"));
    }

}
