package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class ChuckNorrisTest extends EntertainmentFakerTest {

    private final ChuckNorris chuckNorris = getFaker().chuckNorris();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(chuckNorris::fact, "chuck_norris.fact")
        );
    }
}
