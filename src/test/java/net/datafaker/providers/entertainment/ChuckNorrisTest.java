package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class ChuckNorrisTest extends EntertainmentFakerTest {

    private final ChuckNorris chuckNorris = getFaker().chuckNorris();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(chuckNorris::fact, "chuck_norris.fact")
        );
    }
}
