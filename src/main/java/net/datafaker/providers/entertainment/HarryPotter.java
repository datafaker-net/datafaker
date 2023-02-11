package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class HarryPotter extends AbstractProvider<EntertainmentProviders> {

    protected HarryPotter(final EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("harry_potter.characters");
    }

    public String location() {
        return resolve("harry_potter.locations");
    }

    public String quote() {
        return resolve("harry_potter.quotes");
    }

    public String book() {
        return resolve("harry_potter.books");
    }

    public String house() {
        return resolve("harry_potter.houses");
    }

    public String spell() {
        return resolve("harry_potter.spells");
    }
}
