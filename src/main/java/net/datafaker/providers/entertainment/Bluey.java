package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 2.6.0
 */
public class Bluey extends AbstractProvider<EntertainmentProviders> {

    public Bluey(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("bluey.characters");
    }

    public String location() {
        return resolve("bluey.locations");
    }

    public String quote() {
        return resolve("bluey.quotes");
    }
}
