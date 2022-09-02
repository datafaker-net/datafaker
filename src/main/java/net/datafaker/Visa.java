package net.datafaker;

/**
 * @since 1.6.0
 */
public class Visa extends AbstractProvider {
    protected Visa(Faker faker) {
        super(faker);
    }

    public String cardNumber() { return faker.fakeValuesService().resolve("visa.cardNumber", this);}

    public String cardName() { return faker.fakeValuesService().resolve("visa.cardName", this);}

    public String expiryDate() { return faker.fakeValuesService().resolve("visa.expiryDate", this);}

    public String csv() { return faker.fakeValuesService().resolve("visa.csv", this);}
}
