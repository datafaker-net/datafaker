package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class StudioGhibliTest extends EntertainmentFakerTest {

    private final StudioGhibli studioGhibli = getFaker().studioGhibli();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(studioGhibli::character, "studio_ghibli.characters"),
            TestSpec.of(studioGhibli::movie, "studio_ghibli.movies"),
            TestSpec.of(studioGhibli::quote, "studio_ghibli.quotes")
        );
    }
}
