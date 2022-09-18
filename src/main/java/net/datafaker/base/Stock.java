package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Stock extends AbstractProvider<IProviders> {

    protected Stock(BaseFaker faker) {
        super(faker);
    }

    public String nsdqSymbol() {
        return resolve("stock.symbol_nsdq");
    }

    public String nyseSymbol() {
        return resolve("stock.symbol_nyse");
    }

}
