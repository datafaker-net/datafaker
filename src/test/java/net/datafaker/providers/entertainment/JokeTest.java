package net.datafaker.providers.entertainment;

import java.util.Collection;
import java.util.List;

class JokeTest extends EntertainmentFakerTest {

    private final Joke joke = getFaker().joke();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(joke::pun, "joke.puns"),
            TestSpec.of(joke::knockKnock, "joke.knock_knocks")
        );
    }

}
