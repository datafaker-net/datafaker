package net.datafaker.providers.movie;

import net.datafaker.providers.base.BaseFakerTest;

public class MovieFakerTest extends BaseFakerTest<MovieFaker> {
    @Override
    protected MovieFaker getFaker() {
        return new MovieFaker();
    }
}
