package net.datafaker;

import net.datafaker.base.BaseFakerTest;

public class AbstractFakerTest extends BaseFakerTest<Faker> {
    protected Faker getFaker() {
        return new Faker();
    }
}
