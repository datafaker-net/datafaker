package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class MatzTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Matz matz = faker.matz();
        return Arrays.asList(TestSpec.of(matz::quote, "matz.quotes"));
    }
}
