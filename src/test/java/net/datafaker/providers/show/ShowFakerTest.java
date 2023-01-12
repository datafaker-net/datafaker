package net.datafaker.providers.show;

import net.datafaker.providers.base.BaseFakerTest;

public class ShowFakerTest extends BaseFakerTest<ShowFaker> {
    @Override
    protected ShowFaker getFaker() {
        return new ShowFaker();
    }
}
