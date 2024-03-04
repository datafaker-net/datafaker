package net.datafaker.providers.base;

/**
 * @since 0.8.0
 * @deprecated since 2.3.0. For removal in 3.0.0 version. This faker is deprecated due to the existence
 * of duplicate {@link Money} faker with similar methods. Use {@link Money} instead.
 */
@Deprecated(since = "2.3.0", forRemoval = true)
public class Currency extends AbstractProvider<BaseProviders> {

    public Currency(BaseProviders faker) {
        super(faker);
    }

    /**
     * @deprecated since 2.3.0. For removal in 3.0.0 version. Use {@link Money#currency} instead.
     */
    public String name() {
        return resolve("currency.name");
    }

    /**
     * @deprecated since 2.3.0. For removal in 3.0.0 version. Use {@link Money#currencyCode()} instead.
     * @return an alphabetic currency code (ex. USD)
     */
    public String code() {
        return resolve("currency.code");
    }

    /**
     * <p>Returns an active ISO 4217 three-digit numeric currency code as of 1 April 2022</p>
     *
     * @deprecated since 2.3.0. For removal in 3.0.0 version. Use {@link Money#currencyNumericCode()} instead.
     *
     * @return an ISO 4217 currency numeric code
     * @since 2.2.0
     */
    public String numericCode() {
        return resolve("currency.numeric_code");
    }

}
