package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class NigeriaTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Nigeria nigeria = faker.nigeria();
        return List.of(TestSpec.of(nigeria::places, "nigeria.places"),
                TestSpec.of(nigeria::food, "nigeria.food"),
                TestSpec.of(nigeria::name, "nigeria.name"),
                TestSpec.of(nigeria::schools, "nigeria.schools"),
                TestSpec.of(nigeria::celebrities, "nigeria.celebrities"));
    }
}
