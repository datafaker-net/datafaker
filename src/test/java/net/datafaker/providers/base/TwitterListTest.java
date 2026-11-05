package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class TwitterListTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Twitter twitter = faker.twitter();
        return List.of(
            TestSpec.of(twitter::userName, "twitter.user_name"),
            TestSpec.of(twitter::userId, "twitter.user_id"));
    }
}
