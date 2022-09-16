package net.datafaker;

import java.util.function.Supplier;

public class AbstractProvider {
    protected final Faker faker;

    protected AbstractProvider(Faker faker) {
        this.faker = faker;
    }

    public final Faker getFaker() {
        return faker;
    }

    protected String resolve(String key) {
        return faker.fakeValuesService().resolve(key, this, faker.getContext());
    }

    protected String resolve(String key, Supplier<String> message) {
        return faker.fakeValuesService().resolve(key, this, faker, message, faker.getContext());
    }
}
