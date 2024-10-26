package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.9.0
 */
public class Babylon5 extends AbstractProvider<EntertainmentProviders> {

    protected Babylon5(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("babylon5.characters");
    }

    public String quote() {
        return resolve("babylon5.quotes");
    }
}
