package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Artist extends AbstractProvider<BaseProviders> {

    protected Artist(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("artist.names");
    }
}
