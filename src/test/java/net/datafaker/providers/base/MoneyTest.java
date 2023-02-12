package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class MoneyTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Money money = faker.money();
        return List.of(TestSpec.of(money::currency, "money.currency"),
            TestSpec.of(money::currencyCode, "money.code"));
    }
}
