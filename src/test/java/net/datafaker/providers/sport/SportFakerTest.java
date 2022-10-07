package net.datafaker.providers.sport;

import net.datafaker.providers.base.BaseFakerTest;

public class SportFakerTest extends BaseFakerTest<SportFaker> {
    @Override
    protected SportFaker getFaker() {
        return new SportFaker();
    }
}
