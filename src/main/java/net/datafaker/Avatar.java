package net.datafaker;

/**
 * @since 0.8.0
 */
public class Avatar extends AbstractProvider {
    private final String baseUrl;

    protected Avatar(Faker faker) {
        super(faker);
        this.baseUrl = "https://robohash.org/";
    }

    public String image() {
        return baseUrl + faker.fakeValuesService().regexify("[a-z]{8}") + ".png";
    }
}
