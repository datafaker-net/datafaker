package net.datafaker;

/**
 * @since 0.8.0
 */
public class Robin extends AbstractProvider {

    protected Robin(Faker faker) {
        super(faker);
    }

    public String quote() {
        return resolve("robin.quotes");
    }
}
