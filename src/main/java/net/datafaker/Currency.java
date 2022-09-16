package net.datafaker;

/**
 * @since 0.8.0
 */
public class Currency extends AbstractProvider {

    public Currency(Faker faker) {
        super(faker);
    }

    public String name() {
        return resolve("currency.name");
    }

    public String code() {
        return resolve("currency.code");
    }
}
