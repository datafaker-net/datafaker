package net.datafaker.providers.base;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.validator.routines.ISINValidator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;
import java.util.List;

class StockTest extends BaseFakerTest {

    private static final ISINValidator ISIN_VALIDATOR = ISINValidator.getInstance(true);

    @Override
    protected Collection<TestSpec> providerListTest() {
        Stock stock = faker.stock();
        return List.of(TestSpec.of(stock::nsdqSymbol, "stock.symbol_nsdq", "[A-Z]{1,5}"),
                TestSpec.of(stock::nyseSymbol, "stock.symbol_nyse", "[A-Z]{1,4}(\\.[A-Z])?"),
                TestSpec.of(stock::nseSymbol, "stock.symbol_nse", "[A-Z0-9&-]{1,10}"),
                TestSpec.of(stock::lseSymbol, "stock.symbol_lse", "[A-Z0-9]{1,4}(-[A-Z])?"),
                TestSpec.of(stock::tseSymbol, "stock.symbol_tse", "[0-9]{3}[0-9A-Z]"),
                TestSpec.of(stock::exchanges, "stock.exchanges", "[A-Z]+(-[A-Z]+)?"));
    }

    @RepeatedTest(100)
    void isin() {
        assertThat(faker.stock().isin())
            .matches("^[A-Z]{2}[0-9A-Z]{9}[0-9]$")
            .satisfies(isin -> assertThat(ISIN_VALIDATOR.isValid(isin))
            .as("ISIN %s should be valid according to %s", isin, ISIN_VALIDATOR.getClass().getName())
            .isTrue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"symbol_nsdq", "symbol_nyse", "symbol_nse", "symbol_lse", "symbol_tse", "exchanges"})
    void noDuplicates(String key) {
        assertThat(getBaseList(faker, "stock." + key)).doesNotHaveDuplicates();
    }
}
