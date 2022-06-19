package net.datafaker;

import net.datafaker.core.Faker;

/**
 * @since 0.8.0
 */
public class Ancient {

    private final Faker faker;

    protected Ancient(Faker faker) {
        this.faker = faker;
    }

    public String god() {
        return faker.resolve("ancient.god");
    }

    public String primordial() {
        return faker.resolve("ancient.primordial");
    }

    public String titan() {
        return faker.resolve("ancient.titan");
    }

    public String hero() {
        return faker.resolve("ancient.hero");
    }
}
