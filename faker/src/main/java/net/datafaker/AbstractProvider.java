package net.datafaker;

public class AbstractProvider<T extends Faker> {
    protected final T faker;

    protected AbstractProvider(T faker) {
        this.faker = faker;
    }

    public final T getFaker() {
        return faker;
    }
}
