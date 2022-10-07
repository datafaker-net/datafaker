package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Color extends AbstractProvider<BaseProviders> {

    protected Color(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("color.name");
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
