package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

public class CommunityTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Community community = faker.community();
        return List.of(TestSpec.of(community::character, "community.characters"),
                TestSpec.of(community::quote, "community.quotes"));
    }
}
