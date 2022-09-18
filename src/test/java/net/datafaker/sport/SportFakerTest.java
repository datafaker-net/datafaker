package net.datafaker.sport;

import net.datafaker.base.BaseFakerTest;

public class SportFakerTest extends BaseFakerTest<SportFaker> {
    @Override
    protected SportFaker getFaker() {
        return new SportFaker();
    }
}
