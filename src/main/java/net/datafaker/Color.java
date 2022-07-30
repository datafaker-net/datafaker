package net.datafaker;

/**
 * @since 0.8.0
 */
public class Color extends AbstractProvider {

    protected Color(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("color.name", this);
    }

    public String hex() {
        return hex(true);
    }

    public String hex(boolean includeHashSign) {
        String hexString = faker.random().hex(6);
        if (includeHashSign)
            return "#" + hexString;
        return hexString;
    }
}
