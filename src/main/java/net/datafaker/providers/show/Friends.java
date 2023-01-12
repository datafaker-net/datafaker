package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Friends extends AbstractProvider<ShowProviders> {

    protected Friends(ShowProviders faker) {
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
