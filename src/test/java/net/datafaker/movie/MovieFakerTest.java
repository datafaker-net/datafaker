package net.datafaker.movie;

import net.datafaker.base.BaseFakerTest;

public class MovieFakerTest extends BaseFakerTest<MovieFaker> {
    @Override
    protected MovieFaker getFaker() {
        return new MovieFaker();
    }
}
