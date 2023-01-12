package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Supernatural is an American dark fantasy drama television series created by Eric Kripke.
 *
 * @since 1.8.0
 */
public class Supernatural extends AbstractProvider<ShowProviders> {

    protected Supernatural(ShowProviders faker) {
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
