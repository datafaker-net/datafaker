package net.datafaker;

import net.datafaker.core.Faker;

/**
 * @since 0.8.0
 */
public class Currency {

    private final Faker faker;

    public Currency(Faker faker) {
        this.faker = faker;
    }

    public String name() {
        return faker.fakeValuesService().resolve("currency.name", this, faker);
    }

    public String code() {
        return faker.fakeValuesService().resolve("currency.code", this, faker);
    }
}
