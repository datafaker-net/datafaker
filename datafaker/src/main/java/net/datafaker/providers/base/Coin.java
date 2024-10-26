package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Coin extends AbstractProvider<BaseProviders> {

    protected Coin(BaseProviders faker) {
        super(faker);
    }

    /**
     * @return coin side e.g. "Heads", "Tails".
     */
    public String flip() {
        return resolve("coin.flip");
    }
}
