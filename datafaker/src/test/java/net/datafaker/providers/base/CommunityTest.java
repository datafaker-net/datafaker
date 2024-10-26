package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

public class CommunityTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Community community = faker.community();
        return List.of(TestSpec.of(community::character, "community.characters"),
                TestSpec.of(community::quote, "community.quotes"));
    }
}
