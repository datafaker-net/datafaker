package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class AncientTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Ancient ancient = faker.ancient();
        return Arrays.asList(TestSpec.of(ancient::god, "ancient.god"),
            TestSpec.of(ancient::primordial, "ancient.primordial"),
            TestSpec.of(ancient::titan, "ancient.titan"),
            TestSpec.of(ancient::hero, "ancient.hero"));
    }
}
