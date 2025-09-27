package net.datafaker.providers.base;

import net.datafaker.annotations.InternalApi;

import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
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

    @InternalApi
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

    @InternalApi
    protected final <G> List<G> loadGenerators(Class<G> generatorClass) {
        return ServiceLoader.load(generatorClass).stream()
            .map(ServiceLoader.Provider::get)
            .toList();
    }

    @Override
    public String toString() {
        return "%s(%s)@%s".formatted(getClass().getSimpleName(), faker, Integer.toHexString(hashCode()));
    }
}
