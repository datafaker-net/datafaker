package net.datafaker;

import net.datafaker.providers.base.BaseFakerTest;

public class AbstractFakerTest extends BaseFakerTest<Faker> {
    @Override
    protected Faker getFaker() {
        return new Faker();
    }
}
