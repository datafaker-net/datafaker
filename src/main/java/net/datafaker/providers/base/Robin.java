package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Robin extends AbstractProvider<BaseProviders> {

    protected Robin(BaseProviders faker) {
        super(faker);
    }

    public String quote() {
        return resolve("robin.quotes");
    }
}
