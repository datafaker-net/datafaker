package net.datafaker;

/**
 * @since 0.8.0
 */
public class Kaamelott extends AbstractProvider<IProviders> {

    protected Kaamelott(BaseFaker faker) {
        super(faker);
    }

    public String character() {
        return resolve("kaamelott.characters");
    }

    public String quote() {
        return resolve("kaamelott.quotes");
    }
}
