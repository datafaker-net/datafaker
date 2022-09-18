package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class PrincessBride extends AbstractProvider<IProviders> {

    protected PrincessBride(BaseFaker faker) {
        super(faker);
    }

    public String character() {
        return resolve("princess_bride.characters");
    }

    public String quote() {
        return resolve("princess_bride.quotes");
    }
}
