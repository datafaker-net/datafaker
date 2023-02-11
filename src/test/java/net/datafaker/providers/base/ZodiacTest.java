package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class ZodiacTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Zodiac zodiac = faker.zodiac();
        return Arrays.asList(TestSpec.of(zodiac::sign, "zodiac.signs"));
    }
}
