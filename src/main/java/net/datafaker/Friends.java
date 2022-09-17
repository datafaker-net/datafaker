package net.datafaker;

/**
 * @since 0.8.0
 */
public class Friends extends AbstractProvider {

    protected Friends(Faker faker) {
        super(faker);
    }

    public String character() {
        return resolve("friends.characters");
    }

    public String location() {
        return resolve("friends.locations");
    }

    public String quote() {
        return resolve("friends.quotes");
    }
}
