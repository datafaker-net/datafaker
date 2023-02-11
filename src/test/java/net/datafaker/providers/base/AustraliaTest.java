package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class AustraliaTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Australia astralia = faker.australia();
        return Arrays.asList(TestSpec.of(astralia::locations, "australia.locations"),
            TestSpec.of(astralia::animals, "australia.animals"),
            TestSpec.of(astralia::states, "australia.states"));
    }

}
