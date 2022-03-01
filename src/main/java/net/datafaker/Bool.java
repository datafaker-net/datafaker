package net.datafaker;

/**
 * @since 0.8.0
 */
public class Bool {
    private final Faker faker;

    protected Bool(Faker faker) {
        this.faker = faker;
    }

    public boolean bool() {
        return faker.random().nextBoolean();
    }
}
