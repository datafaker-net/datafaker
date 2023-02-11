package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class CryptoCoinTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.cryptoCoin().coin(), "crypto_coin.coin"));
    }
}
