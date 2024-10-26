package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class ZodiacTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Zodiac zodiac = faker.zodiac();
        return List.of(TestSpec.of(zodiac::sign, "zodiac.signs"));
    }
}
