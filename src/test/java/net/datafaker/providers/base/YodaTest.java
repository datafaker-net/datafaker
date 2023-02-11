package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

/**
 * @author Luka Obradovic (luka@vast.com)
 */
class YodaTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.yoda().quote(), "yoda.quotes"));
    }
}
