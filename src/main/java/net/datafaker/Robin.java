package net.datafaker;

/**
 * @since 0.8.0
 */
public class Robin extends AbstractProvider<IProviders> {

    protected Robin(BaseFaker faker) {
        super(faker);
    }

    public String quote() {
        return resolve("robin.quotes");
    }
}
