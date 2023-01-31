package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Luka Obradovic (luka@vast.com)
 */
class YodaTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.yoda().quote(), "yoda.quotes"));
    }
}
