package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class CryptoCoinTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        CryptoCoin cryptoCoin = faker.cryptoCoin();
        return Arrays.asList(TestSpec.of(cryptoCoin::coin, "crypto_coin.coin"));
    }
}
