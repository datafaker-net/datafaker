package net.datafaker;

import net.datafaker.core.Faker;

/**
 * @since 0.8.0
 */
public class BackToTheFuture {
    private final Faker faker;

    protected BackToTheFuture(Faker faker) {
        this.faker = faker;
    }

    public String character() {
        return faker.resolve("back_to_the_future.characters");
    }

    public String date() {
        return faker.resolve("back_to_the_future.dates");
    }

    public String quote() {
        return faker.resolve("back_to_the_future.quotes");
    }
}
