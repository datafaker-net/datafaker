package net.datafaker;

import net.datafaker.core.Faker;

/**
 * @since 0.8.0
 */
public class Bool {
    private final Faker faker;

    public Bool(Faker faker) {
        this.faker = faker;
    }

    public boolean bool() {
        return faker.random().nextBoolean();
    }
}
