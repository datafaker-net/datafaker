package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class CodeListTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(faker.code()::asin, "code.asin"));
    }
}
