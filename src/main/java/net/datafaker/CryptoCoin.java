package net.datafaker;

/**
 * @since 1.3.0
 */
public class CryptoCoin {

    private final Faker faker;

    protected CryptoCoin(Faker faker) {
        this.faker = faker;
    }

    public String coin() {
        return faker.fakeValuesService().resolve("crypto_coin.coin", this, faker);
    }

}
