package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Currency extends AbstractProvider<BaseProviders> {

    public Currency(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("currency.name");
    }

    public String code() {
        return resolve("currency.code");
    }
}
