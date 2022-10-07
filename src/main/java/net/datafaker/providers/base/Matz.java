package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Matz extends AbstractProvider<BaseProviders> {

    protected Matz(final BaseProviders faker) {
        super(faker);
    }

    public String quote() {
        return resolve("matz.quotes");
    }
}
