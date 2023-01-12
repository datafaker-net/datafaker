package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Avatar extends AbstractProvider<ShowProviders> {
    private final String baseUrl;

    protected Avatar(ShowProviders faker) {
        super(faker);
        this.baseUrl = "https://robohash.org/";
    }

    public String image() {
        return baseUrl + faker.regexify("[a-z]{8}") + ".png";
    }
}
