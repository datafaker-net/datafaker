package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class MatzTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Matz matz = faker.matz();
        return List.of(TestSpec.of(matz::quote, "matz.quotes"));
    }
}
