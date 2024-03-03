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

    /**
     * @return an alphabetic currency code (ex. USD)
     */
    public String code() {
        return resolve("currency.code");
    }

    /**
     * <p>Returns an active ISO 4217 three-digit numeric currency code as of 1 April 2022</p>
     *
     * @return an ISO 4217 currency numeric code
     * @since 2.1.1
     */
    public String numericCode() {
        return resolve("currency.numeric_code");
    }

}
