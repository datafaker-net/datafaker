package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.7.0
 */
public class Doraemon extends AbstractProvider<ShowProviders> {

    protected Doraemon(ShowProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("doraemon.characters");
    }

    public String gadget() {
        return resolve("doraemon.gadgets");
    }

    public String location() {
        return resolve("doraemon.locations");
    }

}
