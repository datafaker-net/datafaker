package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class MarketingTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Marketing marketing = faker.marketing();
        return List.of(TestSpec.of(marketing::buzzwords, "marketing.buzzwords"));
    }

}
