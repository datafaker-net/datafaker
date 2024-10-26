package net.datafaker.providers.base;

import java.util.concurrent.TimeUnit;

/**
 * @since 0.8.0
 */
public class Business extends AbstractProvider<BaseProviders> {

    protected Business(BaseProviders faker) {
        super(faker);
    }

    public String creditCardNumber() {
        return faker.numerify(resolve("business.credit_card_numbers_format"));
    }

    public String creditCardType() {
        return resolve("business.credit_card_types");
    }

    public String creditCardExpiry() {
        return faker.date().future(365 * faker.number().numberBetween(1, 20), TimeUnit.DAYS, "YYYY-MM-dd");
    }

    public String securityCode() {
        return faker.numerify(resolve("business.security_code"));
    }
}
