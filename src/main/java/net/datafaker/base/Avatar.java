package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Avatar extends AbstractProvider<IProviders> {
    private final String baseUrl;

    protected Avatar(BaseFaker faker) {
        super(faker);
        this.baseUrl = "https://robohash.org/";
    }

    public String image() {
        return baseUrl + faker.regexify("[a-z]{8}") + ".png";
    }
}
