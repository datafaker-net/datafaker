package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.6.0
 */
public class DumbAndDumber extends AbstractProvider<ShowProviders> {

    protected DumbAndDumber(ShowProviders faker) {
        super(faker);
    }

    public String actor() {
        return resolve("dumb_and_dumber.actors");
    }

    public String character() {
        return resolve("dumb_and_dumber.characters");
    }

    public String quote() {
        return resolve("dumb_and_dumber.quotes");
    }
}
