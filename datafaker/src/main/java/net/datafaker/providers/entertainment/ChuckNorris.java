package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class ChuckNorris extends AbstractProvider<EntertainmentProviders> {

    protected ChuckNorris(EntertainmentProviders faker) {
        super(faker);
    }

    public String fact() {
        return resolve("chuck_norris.fact");
    }
}
