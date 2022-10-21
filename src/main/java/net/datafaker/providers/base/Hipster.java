package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Hipster extends AbstractProvider<BaseProviders> {

    protected Hipster(final BaseProviders faker) {
        super(faker);
    }

    public String word() {
        return resolve("hipster.words");
    }
}
