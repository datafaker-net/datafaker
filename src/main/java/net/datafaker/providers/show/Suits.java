package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

/**
 * Suits is an American legal drama television series created and written by Aaron Korsh.
 *
 * @since 1.8.0
 */
public class Suits extends AbstractProvider<BaseProviders> {

    protected Suits(BaseProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("suits.characters");
    }

    public String quotes() {
        return resolve("suits.quotes");
    }

}
