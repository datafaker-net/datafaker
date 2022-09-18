package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Yoda extends AbstractProvider<IProviders> {

    protected Yoda(final BaseFaker faker) {
        super(faker);
    }

    public String quote() {
        return resolve("yoda.quotes");
    }
}
