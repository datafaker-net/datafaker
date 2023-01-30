package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Kaamelott extends AbstractProvider<EntertainmentProviders> {

    protected Kaamelott(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("kaamelott.characters");
    }

    public String quote() {
        return resolve("kaamelott.quotes");
    }
}
