package net.datafaker;

public class AbstractProvider {
    protected final Faker faker;

    protected AbstractProvider(Faker faker) {
        this.faker = faker;
    }

    public final Faker getFaker() {
        return faker;
    }
}
