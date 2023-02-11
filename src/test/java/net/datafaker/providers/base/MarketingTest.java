package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class MarketingTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Marketing marketing = faker.marketing();
        return Arrays.asList(TestSpec.of(marketing::buzzwords, "marketing.buzzwords"));
    }

}
