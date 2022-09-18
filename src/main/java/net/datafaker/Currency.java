package net.datafaker;

/**
 * @since 0.8.0
 */
public class Currency extends AbstractProvider<IProviders> {

    public Currency(BaseFaker faker) {
        super(faker);
    }

    public String name() {
        return resolve("currency.name");
    }

    public String code() {
        return resolve("currency.code");
    }
}
