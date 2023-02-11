package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class MovieTest extends EntertainmentFakerTest {

    private final Movie movie = getFaker().movie();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(movie::quote, "movie.quote")
        );
    }
}
