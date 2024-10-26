package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class HobbyTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Hobby hobby = faker.hobby();
        return List.of(TestSpec.of(hobby::activity, "hobby.activity"));
    }
}
