package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class AustraliaTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Australia australia = faker.australia();
        return List.of(TestSpec.of(australia::locations, "australia.locations"),
            TestSpec.of(australia::animals, "australia.animals"),
            TestSpec.of(australia::states, "australia.states"));
    }

}
