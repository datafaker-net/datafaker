package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class PhoneNumber extends AbstractProvider<BaseProviders> {

    protected PhoneNumber(BaseProviders faker) {
        super(faker);
    }

    public String cellPhone() {
        return faker.numerify(resolve("cell_phone.formats"));
    }

    /**
     * Generates locale specific phone number in national format.
     *
     * @return phone number
     */
    public String phoneNumber() {
        return phoneNumberNational();
    }

    public String phoneNumberInternational() {
        return faker.numerify(resolve("phone_number.formats_international"));
    }

    public String phoneNumberNational() {
        return faker.numerify(resolve("phone_number.formats"));
    }

    public String extension() {
        return subscriberNumber();
    }

    public String subscriberNumber(int length) {
        return faker.numerify("#".repeat(Math.max(0, length)));
    }

    public String subscriberNumber() {
        return subscriberNumber(4);
    }
}
