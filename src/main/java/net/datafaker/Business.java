package net.datafaker;

/**
 * @since 0.8.0
 */
public class Business extends AbstractProvider {

    protected Business(Faker faker) {
        super(faker);
    }

    public String creditCardNumber() {
        return resolve("business.credit_card_numbers");
    }

    public String creditCardType() {
        return resolve("business.credit_card_types");
    }

    public String creditCardExpiry() {
        return resolve("business.credit_card_expiry_dates");
    }
}
