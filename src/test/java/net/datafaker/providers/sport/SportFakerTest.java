package net.datafaker.providers.sport;

import net.datafaker.providers.base.ProviderListTest;

abstract class SportFakerTest extends ProviderListTest<SportFaker> {

    protected final SportFaker faker = new SportFaker();

    @Override
    protected final SportFaker getFaker() {
        return faker;
    }
}
