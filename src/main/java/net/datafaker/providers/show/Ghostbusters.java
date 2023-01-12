package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.5.0
 */
public class Ghostbusters extends AbstractProvider<ShowProviders> {

    protected Ghostbusters(ShowProviders faker) {
        super(faker);
    }

    public String actor() {
        return resolve("ghostbusters.actors");
    }

    public String character() {
        return resolve("ghostbusters.characters");
    }

    public String quote() {
        return resolve("ghostbusters.quotes");
    }
}
