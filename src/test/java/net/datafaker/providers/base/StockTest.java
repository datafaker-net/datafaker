package net.datafaker.providers.base;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.validator.routines.ISINValidator;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Collection;
import java.util.List;

class StockTest extends BaseFakerTest {

    private static final ISINValidator ISIN_VALIDATOR = ISINValidator.getInstance(true);

    @Override
    protected Collection<TestSpec> providerListTest() {
        Stock stock = faker.stock();
        return List.of(TestSpec.of(stock::nsdqSymbol, "stock.symbol_nsdq"),
                TestSpec.of(stock::nyseSymbol, "stock.symbol_nyse"),
                TestSpec.of(stock::nseSymbol, "stock.symbol_nse"),
                TestSpec.of(stock::lseSymbol, "stock.symbol_lse"),
                TestSpec.of(stock::tseSymbol, "stock.symbol_tse"),
                TestSpec.of(stock::exchanges, "stock.exchanges"));
    }

    @RepeatedTest(100)
    void isin() {
        assertThat(faker.stock().isin())
            .matches("^[A-Z]{2}[0-9A-Z]{9}[0-9]$")
            .satisfies(isin -> assertThat(ISIN_VALIDATOR.isValid(isin))
            .as("ISIN %s should be valid according to %s", isin, ISIN_VALIDATOR.getClass().getName())
            .isTrue());
    }

}
