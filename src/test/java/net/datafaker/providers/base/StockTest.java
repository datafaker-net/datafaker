package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class StockTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Stock stock = faker.stock();
        return List.of(TestSpec.of(stock::nsdqSymbol, "stock.symbol_nsdq"),
                TestSpec.of(stock::nyseSymbol, "stock.symbol_nyse"));
    }

}
