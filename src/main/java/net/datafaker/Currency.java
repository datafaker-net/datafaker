package net.datafaker;

/**
 * @since 0.8.0
 */
public class Currency extends AbstractProvider {

    public Currency(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("currency.name", this);
    }

    public String code() {
        return faker.fakeValuesService().resolve("currency.code", this);
    }
}
