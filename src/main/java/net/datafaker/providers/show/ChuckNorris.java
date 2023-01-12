package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class ChuckNorris extends AbstractProvider<ShowProviders> {

    protected ChuckNorris(ShowProviders faker) {
        super(faker);
    }

    public String fact() {
        return resolve("chuck_norris.fact");
    }
}
