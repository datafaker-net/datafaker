package net.datafaker;

/**
 * @since 0.8.0
 */
public class Business extends AbstractProvider {

    protected Business(Faker faker) {
        super(faker);
    }

    public String creditCardNumber() {
        return faker.fakeValuesService().resolve("business.credit_card_numbers", this);
    }

    public String creditCardType() {
        return faker.fakeValuesService().resolve("business.credit_card_types", this);
    }

    public String creditCardExpiry() {
        return faker.fakeValuesService().resolve("business.credit_card_expiry_dates", this);
    }
}
