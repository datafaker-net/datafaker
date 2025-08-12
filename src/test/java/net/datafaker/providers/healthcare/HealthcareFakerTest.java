package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.BaseFakerTest;

abstract class HealthcareFakerTest extends BaseFakerTest<HealthcareFaker> {
    @Override
    protected final HealthcareFaker getFaker() {
        return new HealthcareFaker();
    }
}
