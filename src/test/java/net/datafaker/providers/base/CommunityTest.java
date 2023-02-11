package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

public class CommunityTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.community().character(), "community.characters"),
                TestSpec.of(() -> faker.community().quote(), "community.quotes"));
    }
}
