package net.datafaker;

/**
 * @since 0.8.0
 */
public class PhoneNumber extends AbstractProvider<IProviders> {

    protected PhoneNumber(BaseFaker faker) {
        super(faker);
    }

    public String cellPhone() {
        return faker.numerify(resolve("cell_phone.formats"));
    }

    public String phoneNumber() {
        return faker.numerify(resolve("phone_number.formats"));
    }

    public String extension() {
        return subscriberNumber();
    }

    public String subscriberNumber(int length) {
        StringBuilder subscriberNumber = new StringBuilder();
        for (int i = 0; i < length; i++) {
            subscriberNumber.append("#");
        }
        return faker.numerify(subscriberNumber.toString());
    }

    public String subscriberNumber() {
        return subscriberNumber(4);
    }
}
