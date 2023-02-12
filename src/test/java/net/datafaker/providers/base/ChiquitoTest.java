package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class ChiquitoTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.chiquito().expressions(), "chiquito.expressions"),
            TestSpec.of(() -> faker.chiquito().terms(), "chiquito.terms"),
            TestSpec.of(() -> faker.chiquito().sentences(), "chiquito.sentences"),
            TestSpec.of(() -> faker.chiquito().jokes(), "chiquito.jokes"));
    }

}
