package net.datafaker;

/**
 * @since 0.9.0
 */
public class Babylon5 extends AbstractProvider<IProviders> {

    protected Babylon5(BaseFaker faker) {
        super(faker);
    }

    public String character() {
        return resolve("babylon5.characters");
    }

    public String quote() {
        return resolve("babylon5.quotes");
    }
}
