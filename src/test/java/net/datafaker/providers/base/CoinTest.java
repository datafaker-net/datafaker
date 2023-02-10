package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class CoinTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Coin coin = faker.coin();
        return Arrays.asList(TestSpec.of(coin::flip, "coin.flip"));
    }
}
