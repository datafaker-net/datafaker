package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class SizeTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Size size = faker.size();
        return List.of(TestSpec.of(size::adjective, "size.adjective", "[a-zA-Z]+(-[a-zA-Z]+)?"));
    }
}
