package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class FriendsTest extends EntertainmentFakerTest {

    private final Friends friends = getFaker().friends();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(friends::character, "friends.characters"),
            TestSpec.of(friends::quote, "friends.quotes"),
            TestSpec.of(friends::location, "friends.locations")
        );
    }
}
