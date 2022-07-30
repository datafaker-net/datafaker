package net.datafaker;

/**
 * @since 1.3.0
 */
public class CryptoCoin extends AbstractProvider {

    protected CryptoCoin(Faker faker) {
        super(faker);
    }

    public String coin() {
        return faker.fakeValuesService().resolve("crypto_coin.coin", this);
    }

}
