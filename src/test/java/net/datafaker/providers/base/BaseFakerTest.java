package net.datafaker.providers.base;

public abstract class BaseFakerTest extends ProviderListTest<BaseFaker> {
    protected final BaseFaker faker = new BaseFaker();

    @Override
    protected final BaseFaker getFaker() {
        return faker;
    }

}
