package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class ChiquitoTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.chiquito().expressions(), "chiquito.expressions"),
            TestSpec.of(() -> faker.chiquito().terms(), "chiquito.terms"),
            TestSpec.of(() -> faker.chiquito().sentences(), "chiquito.sentences"),
            TestSpec.of(() -> faker.chiquito().jokes(), "chiquito.jokes"));
    }

}
