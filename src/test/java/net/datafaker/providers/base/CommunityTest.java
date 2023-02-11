package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

public class CommunityTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Community community = faker.community();
        return Arrays.asList(TestSpec.of(community::character, "community.characters"),
                TestSpec.of(community::quote, "community.quotes"));
    }
}
