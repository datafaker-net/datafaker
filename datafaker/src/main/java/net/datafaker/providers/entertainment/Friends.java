package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Friends extends AbstractProvider<EntertainmentProviders> {

    protected Friends(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("friends.characters");
    }

    public String location() {
        return resolve("friends.locations");
    }

    public String quote() {
        return resolve("friends.quotes");
    }
}
