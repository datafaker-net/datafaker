package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.ProviderListTest;

public abstract class EntertainmentFakerTest extends ProviderListTest<EntertainmentFaker> {

    protected final EntertainmentFaker faker = new EntertainmentFaker();

    @Override
    protected final EntertainmentFaker getFaker() {
        return faker;
    }
}
