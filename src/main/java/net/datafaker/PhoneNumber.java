package net.datafaker;

/**
 * @since 0.8.0
 */
public class PhoneNumber extends AbstractProvider {

    protected PhoneNumber(Faker faker) {
        super(faker);
    }

    public String cellPhone() {
        return faker.numerify(faker.fakeValuesService().resolve("cell_phone.formats", this));
    }

    public String phoneNumber() {
        return faker.numerify(faker.fakeValuesService().resolve("phone_number.formats", this));
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
