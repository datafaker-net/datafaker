package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Luka Obradovic (luka@vast.com)
 */
class YodaTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Yoda yoda = faker.yoda();
        return Arrays.asList(TestSpec.of(yoda::quote, "yoda.quotes"));
    }
}
