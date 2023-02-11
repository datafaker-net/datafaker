package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class StockTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Stock stock = faker.stock();
        return Arrays.asList(TestSpec.of(stock::nsdqSymbol, "stock.symbol_nsdq"),
                TestSpec.of(stock::nyseSymbol, "stock.symbol_nyse"));
    }

}
