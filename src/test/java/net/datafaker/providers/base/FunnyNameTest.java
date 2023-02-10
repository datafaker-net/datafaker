package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class FunnyNameTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        FunnyName funnyName = faker.funnyName();
        return Arrays.asList(TestSpec.of(funnyName::name, "funny_name.name", "^(\\w+\\.?\\s?'?-?)+$"));
    }
}
