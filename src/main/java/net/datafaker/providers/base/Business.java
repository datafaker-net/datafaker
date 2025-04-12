package net.datafaker.providers.base;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * @since 0.8.0
 */
public class Business extends AbstractProvider<BaseProviders> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
        return DATE_FORMATTER.format(LocalDate.now().plus(faker.timeAndDate().period(Period.ZERO, Period.of(20, 0, 0))));
    }

    public String securityCode() {
        return faker.numerify(resolve("business.security_code"));
    }
}
