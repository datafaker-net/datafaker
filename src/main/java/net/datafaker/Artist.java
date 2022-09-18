package net.datafaker;

/**
 * @since 0.8.0
 */
public class Artist extends AbstractProvider<IProviders> {

    protected Artist(BaseFaker faker) {
        super(faker);
    }

    public String name() {
        return  resolve("artist.names");
    }
}
