package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class HipsterTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Hipster hipster = faker.hipster();
        return Arrays.asList(TestSpec.of(hipster::word, "hipster.words", "^([\\w-+&']+ ?)+$"));
    }
}
