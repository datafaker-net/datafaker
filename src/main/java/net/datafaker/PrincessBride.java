package net.datafaker;

/**
 * @since 0.8.0
 */
public class PrincessBride extends AbstractProvider {

    protected PrincessBride(Faker faker) {
        super(faker);
    }

    public String character() {
        return resolve("princess_bride.characters");
    }

    public String quote() {
        return resolve("princess_bride.quotes");
    }
}
