package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class CryptoCoinTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.cryptoCoin().coin(), "crypto_coin.coin"));
    }
}
