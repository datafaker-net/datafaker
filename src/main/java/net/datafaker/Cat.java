package net.datafaker;

import net.datafaker.core.Faker;

/**
 * @since 0.8.0
 */
public class Cat {

    private final Faker faker;

    protected Cat(Faker faker) {
        this.faker = faker;
    }

    public String name() {
        return faker.fakeValuesService().resolve("creature.cat.name", this, faker);
    }

    public String breed() {
        return faker.fakeValuesService().resolve("creature.cat.breed", this, faker);
    }

    public String registry() {
        return faker.fakeValuesService().resolve("creature.cat.registry", this, faker);
    }
}
