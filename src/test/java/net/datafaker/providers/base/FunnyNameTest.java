package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class FunnyNameTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        FunnyName funnyName = faker.funnyName();
        return List.of(TestSpec.of(funnyName::name, "funny_name.name", "^(\\w+\\.?\\s?'?-?)+$"));
    }
}
