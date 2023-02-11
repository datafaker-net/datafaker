package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

public class CommunityTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.community().character(), "community.characters"),
                TestSpec.of(() -> faker.community().quote(), "community.quotes"));
    }
}
