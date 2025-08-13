package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class CryptoCoinTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        CryptoCoin cryptoCoin = faker.cryptoCoin();
        return List.of(TestSpec.of(cryptoCoin::coin, "crypto_coin.coin"));
    }
}
