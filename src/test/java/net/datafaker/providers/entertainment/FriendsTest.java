package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class FriendsTest extends EntertainmentFakerTest {

    private final Friends friends = getFaker().friends();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(friends::character, "friends.characters"),
            TestSpec.of(friends::quote, "friends.quotes"),
            TestSpec.of(friends::location, "friends.locations")
        );
    }
}
