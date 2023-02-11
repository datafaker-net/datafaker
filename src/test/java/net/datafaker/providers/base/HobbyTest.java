package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class HobbyTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.hobby().activity(), "hobby.activity"));
    }
}
