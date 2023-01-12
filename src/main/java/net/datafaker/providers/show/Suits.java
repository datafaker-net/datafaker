package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Suits is an American legal drama television series created and written by Aaron Korsh.
 *
 * @since 1.8.0
 */
public class Suits extends AbstractProvider<ShowProviders> {

    protected Suits(ShowProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("suits.characters");
    }

    public String quotes() {
        return resolve("suits.quotes");
    }

}
