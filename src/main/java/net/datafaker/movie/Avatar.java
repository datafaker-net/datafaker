package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Avatar extends AbstractProvider<MovieProviders> {
    private final String baseUrl;

    protected Avatar(MovieProviders faker) {
        super(faker);
        this.baseUrl = "https://robohash.org/";
    }

    public String image() {
        return baseUrl + faker.regexify("[a-z]{8}") + ".png";
    }
}
