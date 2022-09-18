package net.datafaker;

import net.datafaker.base.AbstractBaseFakerTest;

public class AbstractFakerTest extends AbstractBaseFakerTest<Faker> {
    protected Faker getFaker() {
        return new Faker();
    }
}
