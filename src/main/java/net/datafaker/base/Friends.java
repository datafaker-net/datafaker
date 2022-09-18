package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Friends extends AbstractProvider<IProviders> {

    protected Friends(BaseFaker faker) {
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
