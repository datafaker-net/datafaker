package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Severance is an American science fiction psychological thriller television series
 *
 * @since 2.4.4
 */
public class Severance extends AbstractProvider<EntertainmentProviders> {

    protected Severance(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("severance.characters");
    }

}
