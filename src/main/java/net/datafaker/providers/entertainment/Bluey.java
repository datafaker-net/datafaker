package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

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
