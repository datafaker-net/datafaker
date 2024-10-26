package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.BaseFakerTest;

class HealthcareFakerTest extends BaseFakerTest<HealthcareFaker> {
    @Override
    protected HealthcareFaker getFaker() {
        return new HealthcareFaker();
    }
}
