package net.datafaker;

/**
 * @since 0.8.0
 */
public class ChuckNorris extends AbstractProvider<IProviders> {

    protected ChuckNorris(BaseFaker faker) {
        super(faker);
    }

    public String fact() {
        return resolve("chuck_norris.fact");
    }
}
