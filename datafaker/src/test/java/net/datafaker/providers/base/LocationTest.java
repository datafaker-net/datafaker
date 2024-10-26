package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class LocationTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Location location = faker.location();
        return List.of(
            TestSpec.of(location::building, "location.building"),
            TestSpec.of(location::nature, "location.nature"),
            TestSpec.of(location::otherworldly, "location.otherworldly"),
            TestSpec.of(location::privateSpace, "location.private_space"),
            TestSpec.of(location::publicSpace, "location.public_space"),
            TestSpec.of(location::work, "location.work")
        );
    }
}
