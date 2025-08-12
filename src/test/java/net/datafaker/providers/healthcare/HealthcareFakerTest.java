package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.ProviderListTest;

abstract class HealthcareFakerTest extends ProviderListTest<HealthcareFaker> {

    protected final HealthcareFaker faker = new HealthcareFaker();

    @Override
    protected final HealthcareFaker getFaker() {
        return faker;
    }
}
