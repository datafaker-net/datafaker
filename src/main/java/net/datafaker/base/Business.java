package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Business extends AbstractProvider<BaseProviders> {

    protected Business(BaseProviders faker) {
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
