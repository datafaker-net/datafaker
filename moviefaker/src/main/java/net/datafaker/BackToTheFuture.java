package net.datafaker;

/**
 * @since 0.8.0
 */
public class BackToTheFuture extends MovieProvider {

    protected BackToTheFuture(MovieFaker faker) {
        super(faker);
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
