package net.datafaker.sport;

import net.datafaker.base.AbstractBaseFakerTest;

public class AbstractSportFakerTest extends AbstractBaseFakerTest<SportFaker> {
    @Override
    protected SportFaker getFaker() {
        return new SportFaker();
    }
}
