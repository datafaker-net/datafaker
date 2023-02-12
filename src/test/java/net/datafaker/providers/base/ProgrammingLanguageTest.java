package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class ProgrammingLanguageTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        ProgrammingLanguage lang = faker.programmingLanguage();
        return List.of(TestSpec.of(lang::name, "programming_language.name", "[A-Za-z\\d :,.+*()#/–\\-@πéöü'′!]+"),
            TestSpec.of(lang::creator, "programming_language.creator", "[A-Za-z .,\\-]+"));
    }
}
