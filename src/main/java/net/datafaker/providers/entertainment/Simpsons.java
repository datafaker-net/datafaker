package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.5.0
 */
public class Simpsons extends AbstractProvider<EntertainmentProviders> {

    public Simpsons(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("simpsons.characters");
    }

    public String location() {
        return resolve("simpsons.locations");
    }

    public String quote() {
        return resolve("simpsons.quotes");
    }
}
