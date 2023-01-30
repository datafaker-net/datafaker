package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.5.0
 */
public class Ghostbusters extends AbstractProvider<EntertainmentProviders> {

    protected Ghostbusters(EntertainmentProviders faker) {
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
