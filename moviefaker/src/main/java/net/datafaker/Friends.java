package net.datafaker;

/**
 * @since 0.8.0
 */
public class Friends extends MovieProvider {

    protected Friends(MovieFaker faker) {
        super(faker);
    }

    public String character() {
        return faker.resolve("friends.characters");
    }

    public String location() {
        return faker.resolve("friends.locations");
    }

    public String quote() {
        return faker.resolve("friends.quotes");
    }
}
