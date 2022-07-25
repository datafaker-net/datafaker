package net.datafaker;

/**
 * @since 1.5.0
 */
public class Hololive {
    private final Faker faker;

    protected Hololive(Faker faker) {
        this.faker = faker;
    }

    public String talent() {
        return faker.resolve("hololive.talent");
    }
}
