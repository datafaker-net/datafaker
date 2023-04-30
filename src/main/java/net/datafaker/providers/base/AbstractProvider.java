package net.datafaker.providers.base;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractProvider<?> that)) return false;

        return Objects.equals(faker, that.faker);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
