package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class HipsterTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Hipster hipster = faker.hipster();
        return List.of(TestSpec.of(hipster::word, "hipster.words", "^([\\w-+&']+ ?)+$"));
    }
}
