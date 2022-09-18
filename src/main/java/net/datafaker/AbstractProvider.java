package net.datafaker;

import java.util.function.Supplier;

public class AbstractProvider<T extends ProviderRegistration> {
    protected final T faker;

    protected AbstractProvider(T faker) {
        this.faker = faker;
    }

    public final ProviderRegistration getFaker() {
        return faker;
    }

    protected String resolve(String key) {
        return faker.fakeValuesService().resolve(key, this, faker.getContext());
    }

    protected String resolve(String key, Supplier<String> message) {
        return faker.fakeValuesService().resolve(key, this, faker, message, faker.getContext());
    }
}
