package net.datafaker;

/**
 * @since 0.8.0
 */
public class Hipster extends AbstractProvider<IProviders> {

    protected Hipster(final BaseFaker faker) {
        super(faker);
    }

    public String word() {
        return resolve("hipster.words");
    }
}
