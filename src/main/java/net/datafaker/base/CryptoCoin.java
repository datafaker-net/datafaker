package net.datafaker.base;

/**
 * @since 1.3.0
 */
public class CryptoCoin extends AbstractProvider<IProviders> {

    protected CryptoCoin(BaseFaker faker) {
        super(faker);
    }

    public String coin() {
        return resolve("crypto_coin.coin");
    }

}
