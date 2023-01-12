package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.9.0
 */
public class Babylon5 extends AbstractProvider<ShowProviders> {

    protected Babylon5(ShowProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("babylon5.characters");
    }

    public String quote() {
        return resolve("babylon5.quotes");
    }
}
