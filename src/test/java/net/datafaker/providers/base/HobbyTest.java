package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class HobbyTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.hobby().activity(), "hobby.activity"));
    }
}
