package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Hipster extends AbstractProvider<BaseProviders> {

    protected Hipster(final BaseFaker faker) {
        super(faker);
    }

    public String word() {
        return resolve("hipster.words");
    }
}
