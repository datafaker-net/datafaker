package net.datafaker.providers.show;

import net.datafaker.providers.base.BaseFakerTest;

public class MovieFakerTest extends BaseFakerTest<ShowFaker> {
    @Override
    protected ShowFaker getFaker() {
        return new ShowFaker();
    }
}
