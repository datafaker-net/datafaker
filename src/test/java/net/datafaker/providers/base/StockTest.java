package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class StockTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.stock().nsdqSymbol(), "stock.symbol_nsdq"),
                TestSpec.of(() -> faker.stock().nyseSymbol(), "stock.symbol_nyse"));
    }

}
