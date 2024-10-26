package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

/**
 * @author Luka Obradovic (luka@vast.com)
 */
class YodaTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Yoda yoda = faker.yoda();
        return List.of(TestSpec.of(yoda::quote, "yoda.quotes"));
    }
}
