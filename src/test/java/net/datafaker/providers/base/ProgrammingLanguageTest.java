package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class ProgrammingLanguageTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        ProgrammingLanguage lang = faker.programmingLanguage();
        return List.of(TestSpec.of(lang::name, "programming_language.name", "[A-Za-z\\d :,.+*()#/–\\-@πéöü'′!]+"),
            TestSpec.of(lang::creator, "programming_language.creator", "[A-Za-z .,\\-]+"));
    }
}
