package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class HobbyTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Hobby hobby = faker.hobby();
        return Arrays.asList(TestSpec.of(hobby::activity, "hobby.activity"));
    }
}
