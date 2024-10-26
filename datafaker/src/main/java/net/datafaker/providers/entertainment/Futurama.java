package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Futurama is an American animated science fiction sitcom created by Matt Groening for the Fox Broadcasting Company.
 * 
 * @since 1.8.0
 */
public class Futurama extends AbstractProvider<EntertainmentProviders> {

    protected Futurama(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("futurama.characters");
    }

    public String location() {
        return resolve("futurama.locations");
    }

    public String quote() {
        return resolve("futurama.quotes");
    }

    public String hermesCatchPhrase() {
        return resolve("futurama.hermes_catchphrases");
    }

}
