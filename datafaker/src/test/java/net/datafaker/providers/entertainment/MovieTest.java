package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class MovieTest extends EntertainmentFakerTest {

    private final Movie movie = getFaker().movie();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(movie::quote, "movie.quote")
        );
    }
}
