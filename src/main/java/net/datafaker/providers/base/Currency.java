package net.datafaker.providers.base;

/**
 * @since 0.8.0
 * @deprecated since 2.2.0. For removal in 3.0.0 version. This faker is deprecated due to the existence
 * of duplicate {@link Money} faker with similar methods. Use {@link Money} instead.
 */
@Deprecated(since = "2.2.0", forRemoval = true)
public class Currency extends AbstractProvider<BaseProviders> {

    public Currency(BaseProviders faker) {
        super(faker);
    }

    /**
     * @deprecated since 2.2.0. For removal in 3.0.0 version. Use {@link Money#currency} instead.
     */
    @Deprecated(since = "2.2.0", forRemoval = true)
    public String name() {
        return resolve("currency.name");
    }

    /**
     * @deprecated since 2.2.0. For removal in 3.0.0 version. Use {@link Money#currencyCode()} instead.
     * @return an alphabetic currency code (ex. USD)
     */
    @Deprecated(since = "2.2.0", forRemoval = true)
    public String code() {
        return resolve("currency.code");
    }

}
