package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class CoinTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Coin coin = faker.coin();
        return List.of(TestSpec.of(coin::flip, "coin.flip"));
    }
}
