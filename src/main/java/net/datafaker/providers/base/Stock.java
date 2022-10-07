package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Stock extends AbstractProvider<BaseProviders> {

    protected Stock(BaseProviders faker) {
        super(faker);
    }

    public String nsdqSymbol() {
        return resolve("stock.symbol_nsdq");
    }

    public String nyseSymbol() {
        return resolve("stock.symbol_nyse");
    }

}
