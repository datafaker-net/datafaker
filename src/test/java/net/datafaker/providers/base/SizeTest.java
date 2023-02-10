package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class SizeTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Size size = faker.size();
        return Arrays.asList(TestSpec.of(size::adjective, "size.adjective", "[a-zA-Z]+(-[a-zA-Z]+)?"));
    }
}
