package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class ZodiacTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of((() -> faker.zodiac().sign()), "zodiac.signs"));
    }
}
