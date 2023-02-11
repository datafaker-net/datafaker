package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Supernatural is an American dark fantasy drama television series created by Eric Kripke.
 *
 * @since 1.8.0
 */
public class Supernatural extends AbstractProvider<EntertainmentProviders> {

    protected Supernatural(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("supernatural.character");
    }

    public String creature() {
        return resolve("supernatural.creature");
    }

    public String weapon() {
        return resolve("supernatural.weapon");
    }

}
