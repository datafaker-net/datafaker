package net.datafaker.providers.sport;

import net.datafaker.providers.base.BaseFakerTest;

abstract class SportFakerTest extends BaseFakerTest<SportFaker> {
    @Override
    protected final SportFaker getFaker() {
        return new SportFaker();
    }
}
