package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Robin extends AbstractProvider<BaseProviders> {

    protected Robin(BaseFaker faker) {
        super(faker);
    }

    public String quote() {
        return resolve("robin.quotes");
    }
}
